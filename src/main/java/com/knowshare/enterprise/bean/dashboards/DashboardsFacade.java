/**
 * 
 */
package com.knowshare.enterprise.bean.dashboards;

import java.util.List;
import java.util.Map;

/**
 * Genera la información necesario para construir el 
 * dashboard respectivo
 * @author Felipe Bautista
 *
 */
public interface DashboardsFacade {

	/**
	 * Dashboard que representa la información de
	 * género de usuarios por cada carrera.
	 * @param carrera
	 * @return Lista en donde la primera posición representa
	 * el género 'Masculino' y la segunda posición el
	 * género 'Femenino'
	 */
	List<Integer> generoCarrera(String carrera);
	
	/**
	 * Dashboard que representa la información de uso de tags.
	 * Solo se muestran los tags en uso.
	 * @return Mapa que contiene en la llave el nombre del tag
	 * y como valor la cantidad de veces usado.
	 */
	Map<String,Integer> usoTags();
}
