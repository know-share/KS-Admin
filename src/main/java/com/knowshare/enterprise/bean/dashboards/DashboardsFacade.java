/**
 * 
 */
package com.knowshare.enterprise.bean.dashboards;

import java.util.List;
import java.util.Map;



/**
 * @author Pipe
 *
 */
public interface DashboardsFacade {

	List<Integer> generoCarrera(String carrera);
	Map<String,Integer> usoTags();
}
