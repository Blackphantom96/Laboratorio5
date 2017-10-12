/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.persistence.mybatis;

import com.google.inject.Inject;
import edu.eci.pdsw.persistence.PacienteDAO;
import edu.eci.pdsw.persistence.PersistenceException;
import edu.eci.pdsw.persistence.mappers.PacienteMapper;
import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Paciente;
import java.util.List;

/**
 *
 * @author blackphantom
 */
public class PacienteDAOMyBATIS implements PacienteDAO {

    @Inject
    PacienteMapper pMapper;

    @Override
    public List<Paciente> load() throws PersistenceException {
        try {
            return pMapper.loadPacientes();
        } catch (Exception ex) {
            throw new PersistenceException("Error al cargar los pacientes"+ex);
        }
    }

    @Override
    public Paciente loadById(int id, String tipoId) throws PersistenceException {
        try {
            return pMapper.getPaciente(id, tipoId);
        } catch (Exception ex) {
            throw new PersistenceException("Error al cargar el paciente" + tipoId + id + "%n" + ex);
        }
    }

    @Override
    public void save(Paciente p) throws PersistenceException {
        try {
            pMapper.insertarPaciente(p);
            for (Consulta c : p.getConsultas()) {
                pMapper.insertConsulta(c, p.getId(), p.getTipoId(), (int) c.getCosto());
            }
        } catch (Exception ex) {
            throw new PersistenceException("Error al guardar el paciente" + p + "%n" + ex);
        }
    }

    @Override
    public void update(Paciente p) throws PersistenceException {
        try {
            for (Consulta c : p.getConsultas()) {
                if (c.getId() == 0) {
                    pMapper.insertConsulta(c, p.getId(), p.getTipoId(), (int) c.getCosto());
                }
            }
            pMapper.actualizarPaciente(p);
        } catch (Exception ex) {
            throw new PersistenceException("Error al actualizar el pasiente" + p + "%n" + ex);
        }
    }
}
