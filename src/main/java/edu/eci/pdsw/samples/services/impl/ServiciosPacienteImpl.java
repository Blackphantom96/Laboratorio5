/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.services.impl;

import com.google.inject.Inject;
import edu.eci.pdsw.persistence.EPSDAO;
import edu.eci.pdsw.persistence.PacienteDAO;
import edu.eci.pdsw.persistence.PersistenceException;
import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Eps;
import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.services.ExcepcionServiciosPacientes;
import edu.eci.pdsw.samples.services.ServiciosPacientes;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.mybatis.guice.transactional.Transactional;

/**
 *
 * @author blackphantom
 */
public class ServiciosPacienteImpl implements ServiciosPacientes {

    @Inject
    private PacienteDAO pDAO;

    @Inject
    private EPSDAO eDAO;

    @Transactional
    @Override
    public Paciente consultarPaciente(int idPaciente, String tipoid) throws ExcepcionServiciosPacientes {
        try {
            return pDAO.loadById(idPaciente, tipoid);
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosPacientes("no se encontro el paciente" + idPaciente);
        }
    }

    @Transactional
    @Override
    public List<Paciente> consultarPacientes() throws ExcepcionServiciosPacientes {
        try {
            return pDAO.load();
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosPacientes("hubo un error al cargar los pacientes");
        }
    }

    @Transactional
    @Override
    public void registrarNuevoPaciente(Paciente paciente) throws ExcepcionServiciosPacientes {
        try {
            pDAO.save(paciente);
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosPacientes("no se pudo agregar el paciente" + paciente.getNombre());
        }
    }

    @Transactional
    @Override
    public void agregarConsultaPaciente(int idPaciente, String tipoid, Consulta consulta) throws ExcepcionServiciosPacientes {
        try {
            Paciente p = consultarPaciente(idPaciente, tipoid);
            Set<Consulta> consultas = p.getConsultas();
            consultas.add(consulta);
            p.setConsultas(consultas);
            pDAO.update(p);
        } catch (PersistenceException ex) {
            System.err.println(ex);
            throw new ExcepcionServiciosPacientes("no se pudo agregar la consulta al paciente " + idPaciente);
        }
    }

    @Transactional
    @Override
    public List<Consulta> obtenerConsultasEpsPorFecha(String nameEps, Date fechaInicio, Date fechaFin) throws ExcepcionServiciosPacientes {
        List<Consulta> temp = new ArrayList<>();
        for (Paciente paciente : consultarPacientes()) {
            if (paciente.getEps().getNombre().equals(nameEps)) {
                for (Consulta consulta : paciente.getConsultas()) {
                    if (consulta.getFechayHora().before(fechaFin) && consulta.getFechayHora().after(fechaInicio)) {
                        temp.add(consulta);
                    }
                }
            }
        }
        return temp;
    }

    @Transactional
    @Override
    public long obtenerCostoEpsPorFecha(String nameEps, Date fechaInicio, Date fechaFin) throws ExcepcionServiciosPacientes {
        long deuda = 0;
        for (Paciente paciente : consultarPacientes()) {
            if (paciente.getEps().getNombre().equals(nameEps)) {
                for (Consulta consulta : paciente.getConsultas()) {
                    if (consulta.getFechayHora().after(fechaInicio) && consulta.getFechayHora().before(fechaFin)) {
                        deuda += consulta.getCosto();
                    }
                }
            }
        }
        return deuda;
    }

    @Transactional
    @Override
    public List<Consulta> obtenerConsultasEps(String nameEps) throws ExcepcionServiciosPacientes {
        List<Consulta> temp = new ArrayList<>();
        for (Paciente paciente : consultarPacientes()) {
            if (paciente.getEps().getNombre().equals(nameEps)) {
                temp.addAll(paciente.getConsultas());
            }
        }
        if (temp.isEmpty()) {
            throw new ExcepcionServiciosPacientes("La EPS " + nameEps + " no se encuentra asociada a ningun paciente o no existe ");
        }
        return temp;
    }

    @Transactional
    @Override
    public List<Eps> obtenerEPSsRegistradas() throws ExcepcionServiciosPacientes {
        try {
            return eDAO.load();
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosPacientes("no se pudo cargar las eps");
        }
    }

}
