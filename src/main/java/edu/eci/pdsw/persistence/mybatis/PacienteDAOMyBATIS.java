/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.persistence.mybatis;

import com.google.inject.Inject;
import edu.eci.pdsw.persistence.PacienteDAO;
import edu.eci.pdsw.persistence.mappers.PacienteMapper;
import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Paciente;
import java.util.List;

/**
 *
 * @author blackphantom
 */
public class PacienteDAOMyBATIS implements PacienteDAO{
    @Inject
    PacienteMapper pMapper;
    
    @Override
    public List<Paciente> load() {
        return pMapper.loadPacientes();
    }

    @Override
    public Paciente loadById(int id, String tipoId) {
        return pMapper.getPaciente(id, tipoId);
    }

    @Override
    public void save(Paciente p) {
        pMapper.insertarPaciente(p);
        for(Consulta c:p.getConsultas())
            pMapper.insertConsulta(c, p.getId(), p.getTipoId(), (int) c.getCosto());
    }

    @Override
    public void update(Paciente p) {
        Paciente temp = pMapper.getPaciente(p.getId(), p.getTipoId());
        for(Consulta c:p.getConsultas()){
            if(c.getId()==0)
                pMapper.insertConsulta(c, p.getId(), p.getTipoId(), (int)c.getCosto());
        }
        pMapper.actualizarPaciente(p);
    }
}
