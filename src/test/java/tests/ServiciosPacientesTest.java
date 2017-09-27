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

}
