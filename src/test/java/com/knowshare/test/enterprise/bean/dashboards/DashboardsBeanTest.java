/**
 * 
 */
package com.knowshare.test.enterprise.bean.dashboards;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.knowshare.enterprise.bean.dashboards.DashboardsFacade;
import com.knowshare.test.enterprise.general.AbstractTest;

/**
 * @author Miguel Montañez
 *
 */
public class DashboardsBeanTest extends AbstractTest{

	@Autowired
	private DashboardsFacade dashboardsBean;
	
	@Test
	public void generoCarreraTest(){
		List<Integer> estadisticasGenero = dashboardsBean.generoCarrera("Ingeniería de sistemas");
		assertNotNull(estadisticasGenero);
		assertEquals(2, estadisticasGenero.size());
		assertEquals(Integer.valueOf(2), estadisticasGenero.get(0));
		assertEquals(Integer.valueOf(1), estadisticasGenero.get(1));
		
		estadisticasGenero = dashboardsBean.generoCarrera("Ingeniería civil");
		assertNotNull(estadisticasGenero);
		assertEquals(2, estadisticasGenero.size());
		assertEquals(Integer.valueOf(2), estadisticasGenero.get(1));
		assertEquals(Integer.valueOf(1), estadisticasGenero.get(0));
		
		estadisticasGenero = dashboardsBean.generoCarrera("No existe");
		assertNotNull(estadisticasGenero);
		assertEquals(2, estadisticasGenero.size());
		assertEquals(Integer.valueOf(0), estadisticasGenero.get(1));
		assertEquals(Integer.valueOf(0), estadisticasGenero.get(0));
	}
	
	@Test
	public void usoTagsTest(){
		Map<String, Integer> usoTags = dashboardsBean.usoTags();
		assertNotNull(usoTags);
		assertEquals(4, usoTags.keySet().size());
		assertEquals(Integer.valueOf(2), usoTags.get("TAG1"));
		assertEquals(Integer.valueOf(3), usoTags.get("TAG2"));
		assertEquals(Integer.valueOf(2), usoTags.get("TAG3"));
		assertEquals(Integer.valueOf(3), usoTags.get("TAG4"));
	}
	
	@AfterClass
	public static void tearDown(){
	}
}
