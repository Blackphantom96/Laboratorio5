/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.managebeans;

import edu.eci.pdsw.samples.entities.Eps;
import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.services.ExcepcionServiciosPacientes;
import edu.eci.pdsw.samples.services.ServiciosPacientes;
import edu.eci.pdsw.samples.services.impl.ServiciosPacientesMock;
import java.io.Serializable;
import java.util.*;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author blackphantom
 */
@ManagedBean(name = "RegistroPacientesBean")
@SessionScoped
public class RegistroPacientesBean implements Serializable {

    private ServiciosPacientes sp;
    private Paciente pacienteAgrega;
    public RegistroPacientesBean() throws ExcepcionServiciosPacientes {
        sp = new ServiciosPacientesMock();
        pacienteAgrega=new Paciente();
    }

    public Paciente getPacienteAgrega() {
        return pacienteAgrega;
    }

    public void setPacienteAgrega(Paciente pacienteAgrega) {
        this.pacienteAgrega = pacienteAgrega;
    }

    public List<Paciente> getPacientes() throws ExcepcionServiciosPacientes {
        return sp.consultarPacientes();
    }

    public List<Eps> getEps() throws ExcepcionServiciosPacientes {
        return sp.obtenerEPSsRegistradas();
    }

    public ServiciosPacientes getSp() {
        return sp;
    }
    
    public void addPaciente() throws ExcepcionServiciosPacientes{
        sp.registrarNuevoPaciente(pacienteAgrega);
        pacienteAgrega=new Paciente();
    }

}
