/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.services.impl;

import com.google.inject.Inject;
import edu.eci.pdsw.persistence.EPSDAO;
import edu.eci.pdsw.persistence.PacienteDAO;
import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Eps;
import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.services.ExcepcionServiciosPacientes;
import edu.eci.pdsw.samples.services.ServiciosPacientes;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 *
 * @author blackphantom
 */
public class ServiciosPacienteImpl implements ServiciosPacientes {

    @Inject
    PacienteDAO pDAO;

    @Inject
    EPSDAO eDAO;

    @Override
    public Paciente consultarPaciente(int idPaciente, String tipoid) throws ExcepcionServiciosPacientes {
        return pDAO.loadById(idPaciente, tipoid);
    }

    @Override
    public List<Paciente> consultarPacientes() throws ExcepcionServiciosPacientes {
        return pDAO.load();
    }

    @Override
    public void registrarNuevoPaciente(Paciente paciente) throws ExcepcionServiciosPacientes {
        pDAO.save(paciente);
    }

    @Override
    public void agregarConsultaPaciente(int idPaciente, String tipoid, Consulta consulta) throws ExcepcionServiciosPacientes {
        Paciente p = consultarPaciente(idPaciente, tipoid);
        Set<Consulta> consultas = p.getConsultas();
        consultas.add(consulta);
        p.setConsultas(consultas);
        pDAO.update(p);
    }

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

    @Override
    public List<Eps> obtenerEPSsRegistradas() throws ExcepcionServiciosPacientes {
        return eDAO.load();
    }

}
