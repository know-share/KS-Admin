package com.knowshare.enterprise.bean.dashboards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.knowshare.enterprise.repository.idea.IdeaRepository;
import com.knowshare.enterprise.repository.perfilusuario.UsuarioRepository;
import com.knowshare.entities.academia.Carrera;
import com.knowshare.entities.idea.Idea;
import com.knowshare.entities.idea.Tag;
import com.knowshare.entities.perfilusuario.Usuario;

/**
 * {@link DashboardsFacade}
 * @author Felipe Bautista
 *
 */
@Component
public class DashboardsBean implements DashboardsFacade {

	@Autowired
	private UsuarioRepository usuarioRepository;
	 
	@Autowired
	private IdeaRepository ideaRepository;
	
	@Override
	public List<Integer> generoCarrera(String carrera) {
		List<Usuario> usuarios = usuarioRepository.findAll();
		List<Integer> finalRet = new ArrayList<>();
		finalRet.add(0);// M
		finalRet.add(0);//F
		for(Usuario us: usuarios) {
			if(us.getCarreras()!=null) {
				for(Carrera ca: us.getCarreras()) {
					if(ca.getNombre().equalsIgnoreCase(carrera)) {
						if(us.getGenero().equalsIgnoreCase("Masculino")) {
							finalRet.set(0, finalRet.get(0)+1);
						}	
						else if(us.getGenero().equalsIgnoreCase("Femenino"))
							finalRet.set(1, finalRet.get(1)+1);
					}
				}
			}	
		}
		
		return finalRet;
	}

	@Override
	public Map<String, Integer> usoTags() {
		Map<String,Integer> map = new HashMap<>();
		List<Idea> ideas = ideaRepository.findAll();
		for(Idea i:ideas){
			if(!i.getTags().isEmpty()){
				for(Tag t: i.getTags()){
					if(!map.containsKey(t.getNombre())){
						map.put(t.getNombre(),1);
					}
					else{
						int v = map.get(t.getNombre());
						map.replace(t.getNombre(),v+1);
					}
				}	
			}
		}
		return map;
	}
}
