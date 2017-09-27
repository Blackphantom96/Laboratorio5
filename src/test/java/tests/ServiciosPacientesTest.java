/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Eps;
import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.services.ExcepcionServiciosPacientes;
import edu.eci.pdsw.samples.services.ServiciosPacientes;
import edu.eci.pdsw.samples.services.impl.ServiciosPacientesMock;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
/**
 *
 * CE1: Si el paciente existe lo agrega resultado: agrega la consulta
 *
 * CE2: Si el paciente no existe resultado: Error
 *
 * CE3:Si la EPS y los datos son correctos lo agrega resultado : agrega el paciente
 * 
 * CE4: No deberia agregar el paciente si la eps no existe o no esta registrada resultado error.
 *
 *
 */
public class ServiciosPacientesTest {

    ServiciosPacientes serv;

    public ServiciosPacientesTest() {
    }

    @Before
    public void setUp() {
    }

    @Test
    public void claseDeEquivalencia1() throws ExcepcionServiciosPacientes {
        serv = new ServiciosPacientesMock();
        int x = serv.obtenerConsultasEps("Compensar").size();
        Consulta consulta1 = new Consulta(java.sql.Date.valueOf("2020-01-01"), "Dolor de cabeza", 13445);
        try {
            serv.agregarConsultaPaciente(1, "Cc", consulta1);
            assertEquals("no esta agregando", x, serv.obtenerConsultasEps("Compensar").size() - 1);
        } catch (ExcepcionServiciosPacientes ex) {
            Logger.getLogger(ServiciosPacientesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void claseDeEquivalencia2() throws ExcepcionServiciosPacientes {
        serv = new ServiciosPacientesMock();
        int x = serv.obtenerConsultasEps("Compensar").size();
        Consulta consulta1 = new Consulta(java.sql.Date.valueOf("2020-01-01"), "Dolor de cabeza", 13445);
        try {
            serv.agregarConsultaPaciente(0, "Cc", consulta1);
            fail("esta agregando a un paciente inexistente");
        } catch (ExcepcionServiciosPacientes ex) {
            Logger.getLogger(ServiciosPacientesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void claseDeEquivalencia3() {
        serv = new ServiciosPacientesMock();
        List<Eps> e;
        try {
            int x = serv.consultarPacientes().size();
            e = serv.obtenerEPSsRegistradas();
            Paciente p = new Paciente(1007013, "Cc", "Juan Moreno", new Date(2000, 2, 1), e.get(0));
            serv.registrarNuevoPaciente(p);
            assertEquals("no esta agregando los pacientes", x,serv.consultarPacientes().size()-1);
        } catch (ExcepcionServiciosPacientes ex) {
            Logger.getLogger(ServiciosPacientesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void claseDeEquivalencia4() {
        serv = new ServiciosPacientesMock();
        Paciente p = new Paciente(1007013, "Cc", "Juan Moreno", new Date(2100, 2, 1), new Eps("asdaad", "12312313-2"));
        try {
            serv.registrarNuevoPaciente(p);
            fail("No deberia agregar el paciente");
        } catch (ExcepcionServiciosPacientes ex) {
            Logger.getLogger(ServiciosPacientesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
