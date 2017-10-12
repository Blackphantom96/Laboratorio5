/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.persistence.mybatis;

import com.google.inject.Inject;
import edu.eci.pdsw.persistence.EPSDAO;
import edu.eci.pdsw.persistence.PersistenceException;
import edu.eci.pdsw.persistence.mappers.EpsMapper;
import edu.eci.pdsw.samples.entities.Eps;
import java.util.List;

/**
 *
 * @author blackphantom
 */
public class EPSDAOMyBATIS implements EPSDAO {

    @Inject
    EpsMapper eMapper;

    @Override
    public List<Eps> load() throws PersistenceException {
        try {
            return eMapper.loadAllEPS();
        } catch (Exception e) {
            throw new PersistenceException("Error al argas las eps %n" + e);
        }
    }

    @Override
    public Eps loadById(int nit) throws PersistenceException {
        try {
            return eMapper.loadEPSByNit(nit);
        } catch (Exception e) {
            throw new PersistenceException("Error al cargar la eps" + nit + "%n" + e);
        }
    }

    @Override
    public void save(Eps e) throws PersistenceException {
        try {
            eMapper.registreEps(e);
        } catch (Exception ex) {
            throw new PersistenceException("Error al guardar la eps" + e.getNombre()+ "%n" + ex);
        }
    }

    @Override
    public void update(Eps e) throws PersistenceException {
        try {
            eMapper.udpateEps(e);
        } catch (Exception ex) {
            throw new PersistenceException("Error al actualizar la eps "+ e.getNombre()+ "%n"  + ex);
        }
    }

}
