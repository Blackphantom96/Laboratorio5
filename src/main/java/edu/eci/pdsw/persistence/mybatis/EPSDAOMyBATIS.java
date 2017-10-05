/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.persistence.mybatis;

import com.google.inject.Inject;
import edu.eci.pdsw.persistence.EPSDAO;
import edu.eci.pdsw.persistence.mappers.EpsMapper;
import edu.eci.pdsw.samples.entities.Eps;
import java.util.List;

/**
 *
 * @author blackphantom
 */
public class EPSDAOMyBATIS implements EPSDAO{
    @Inject
    EpsMapper eMapper;

    @Override
    public List<Eps> load() {
        return eMapper.loadAllEPS();
    }

    @Override
    public Eps loadById(int nit) {
        return eMapper.loadEPSByNit(nit);
    }

    @Override
    public void save(Eps e) {
        eMapper.registreEps(e);
    }

    @Override
    public void update(Eps e) {
        eMapper.udpateEps(e);
    }
    
}
