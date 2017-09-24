/**
 * 
 */
package com.knowshare.test.enterprise.general;

import java.io.IOException;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.knowshare.enterprise.bean.dashboards.DashboardsBean;
import com.knowshare.enterprise.bean.dashboards.DashboardsFacade;
import com.knowshare.entities.academia.Carrera;
import com.knowshare.entities.idea.Idea;
import com.knowshare.entities.idea.Tag;
import com.knowshare.entities.perfilusuario.Habilidad;
import com.knowshare.entities.perfilusuario.Usuario;
import com.mongodb.MongoClient;

/**
 * @author Miguel Montañez
 *
 */
@Lazy
@Configuration
@EnableMongoRepositories(basePackages = { "com.knowshare.enterprise.repository" })
@PropertySource("classpath:test.properties")
public class ConfigContext {
	
	@Autowired
	private Environment env;
	
	@PostConstruct
	public void initData() throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		
		Tag[] tags = mapper.readValue(
				ResourceUtils.getURL("classpath:data/tags.json").openStream(),Tag[].class);
		Carrera[] carreras = mapper.readValue(
				ResourceUtils.getURL("classpath:data/carreras.json").openStream(),Carrera[].class);
		Usuario[] usuarios = mapper.readValue(
				ResourceUtils.getURL("classpath:data/usuarios.json").openStream(),Usuario[].class);
		Idea[] ideas = mapper.readValue(
				ResourceUtils.getURL("classpath:data/ideas.json").openStream(),Idea[].class);
		
		this.mongoTemplate().insertAll(Arrays.asList(tags));
		this.mongoTemplate().insertAll(Arrays.asList(carreras));
		this.mongoTemplate().insertAll(Arrays.asList(usuarios));
		this.mongoTemplate().insertAll(Arrays.asList(ideas));
		
		this.createIndexes();
		String command = "mongodump --host " +env.getProperty("db.host") + " --port " + env.getProperty("db.port")
	            + " -d " + env.getProperty("db.name") +" -o \"./target/\"";
		Runtime.getRuntime().exec(command);
	}
	
	@Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(
        		new MongoClient(env.getProperty("db.host"),
        						Integer.parseInt(env.getProperty("db.port"))),
        			env.getProperty("db.name"));
    }
	
	@Bean
	public DashboardsFacade getDashboardsFacade(){
		return new DashboardsBean();
	}

	@PreDestroy
	public void destroy() throws IOException{
		this.mongoTemplate().getDb().dropDatabase();
	}
	
	// ---------------------------
	// PRIVATE METHODS
	// ---------------------------
	
	private void createIndexes(){
		//db.habilidad.createIndex({'nombre':'text'},{ default_language: 'spanish' });
		TextIndexDefinition textIndex = TextIndexDefinition.builder()
				.onField("nombre")
				.withDefaultLanguage("spanish")
				.build();
		this.mongoTemplate().indexOps(Habilidad.class).ensureIndex(textIndex);
		
		//db.usuario.createIndex( { 'nombre': 'text','apellido':'text' },{ default_language: 'spanish' } );
		textIndex = TextIndexDefinition.builder()
				.onField("nombre")
				.onField("apellido")
				.withDefaultLanguage("spanish")
				.build();
		this.mongoTemplate().indexOps(Usuario.class).ensureIndex(textIndex);
	}
}
