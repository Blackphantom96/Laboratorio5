/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.persistence.mappers;
import edu.eci.pdsw.samples.entities.Eps;
import java.util.List;
import org.apache.ibatis.annotations.Param;
/**
 *
 * @author blackphantom
 */
public interface EpsMapper {
    public List<Eps> loadAllEPS();
    
    public Eps loadEPSByNit(@Param("nit") int nit);
    
    public void registreEps(@Param("e")Eps e);
    
    public void udpateEps(@Param("e")Eps e);
}
