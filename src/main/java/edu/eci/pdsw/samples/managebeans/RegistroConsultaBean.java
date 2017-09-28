/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.managebeans;

import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Eps;
import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.services.ExcepcionServiciosPacientes;
import edu.eci.pdsw.samples.services.ServiciosHistorialPacientesFactory;
import edu.eci.pdsw.samples.services.ServiciosPacientes;
import edu.eci.pdsw.samples.services.impl.ServiciosPacientesMock;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author 2106913
 */
@ManagedBean(name = "HistorialPacientes")
@SessionScoped
public class RegistroConsultaBean implements Serializable {

    private Paciente pacienteSeleccionado;
    public static final ServiciosPacientes servicepacientes= ServiciosHistorialPacientesFactory.getInstance().getServiciosPaciente();;
    private Consulta consultaAgrega;
    private Paciente pacienteAgrega;
    public RegistroConsultaBean() {
        pacienteAgrega=new Paciente();
        consultaAgrega = new Consulta();
    }
    
    public List<Paciente> getPacientes() throws ExcepcionServiciosPacientes{
        return servicepacientes.consultarPacientes();
    }
    
    public List<Eps> getEps() throws ExcepcionServiciosPacientes{
        return servicepacientes.obtenerEPSsRegistradas();
    }

    public Consulta getConsultaAgrega() {
        return consultaAgrega;
    }

    public void setConsultaAgrega(Consulta consultaAgrega) {
        this.consultaAgrega = consultaAgrega;
    }

    public Paciente getPacienteSeleccionado() {
        return pacienteSeleccionado;
    }

    public Set<Consulta> getConsultas() {
        return pacienteSeleccionado.getConsultas();
    }

    public void setPacienteSeleccionado(Paciente pacienteSeleccionado) {
        this.pacienteSeleccionado = pacienteSeleccionado;
    }

    public Paciente getPacienteAgrega() {
        return pacienteAgrega;
    }

    public void setPacienteAgrega(Paciente pacienteAgrega) {
        this.pacienteAgrega = pacienteAgrega;
    }
    

    public void agregarConsulta() throws ExcepcionServiciosPacientes {
        servicepacientes.agregarConsultaPaciente(pacienteSeleccionado.getId(),
                pacienteSeleccionado.getTipoId(), consultaAgrega);
        consultaAgrega = new Consulta();
    }
    public void addPaciente() throws ExcepcionServiciosPacientes{
        servicepacientes.registrarNuevoPaciente(pacienteAgrega);
        pacienteAgrega=new Paciente();
    }
    public void showMessage(String estado, String mensaje) {
        FacesMessage message;
        message = new FacesMessage(FacesMessage.SEVERITY_INFO, estado, mensaje);
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }

}
