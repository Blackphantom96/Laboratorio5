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
import edu.eci.pdsw.samples.services.ServiciosHistorialPacientesFactory;
import edu.eci.pdsw.samples.services.ServiciosPacientes;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * CE3:Si la EPS y los datos son correctos lo agrega resultado : agrega el
 * paciente
 *
 * CE4: No deberia agregar el paciente si la eps no existe o no esta registrada
 * resultado error.
 *
 *
 */
public class ServiciosPacientesTest {

    ServiciosPacientes serv = ServiciosHistorialPacientesFactory.getInstance().getTestingServiciosPaciente();

    public ServiciosPacientesTest() {
    }

    @Before
    public void setUp() {
    }

    @Test
    public void claseDeEquivalencia1() {
        try {
            Paciente p = serv.consultarPacientes().get(0);
            int x = serv.obtenerConsultasEps(p.getEps().getNombre()).size();
            Consulta consulta1 = new Consulta(java.sql.Date.valueOf("2017-01-01"), "Dolor de cabeza", 13445);
            serv.agregarConsultaPaciente(p.getId(), p.getTipoId(), consulta1);
            assertEquals("no esta agregando la consulta", x, serv.obtenerConsultasEps(p.getEps().getNombre()).size() - 1);
        } catch (Exception e) {
            
        }
    }

    @Test
    public void claseDeEquivalencia2() {
        Consulta consulta1 = new Consulta(java.sql.Date.valueOf("2020-01-01"), "Dolor de cabeza", 13445);
        try {
            serv.agregarConsultaPaciente(0, "Cc", consulta1);
            fail("esta agregando a un paciente inexistente");
        } catch (Exception e) {

        }
    }

    @Test
    public void claseDeEquivalencia3() {
        try {
            int x = serv.consultarPacientes().size();
            List<Eps> e = serv.obtenerEPSsRegistradas();
            Paciente p = new Paciente(1007013, "Cc", "Juan Moreno", new Date(2000, 2, 1), e.get(0));
            serv.registrarNuevoPaciente(p);
            assertEquals("no esta agregando los pacientes", x, serv.consultarPacientes().size() - 1);
        } catch (ExcepcionServiciosPacientes ex) {
            Logger.getLogger(ServiciosPacientesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void claseDeEquivalencia4() {
        Paciente p = new Paciente(1007013, "Cc", "Juan Moreno eps no", new Date(2000, 2, 1), new Eps("asdaad", "12312313"));
        try {
            serv.registrarNuevoPaciente(p);
            for(Paciente g: serv.consultarPacientes())
                System.err.println(g);
            fail("No deberia agregar el paciente");
        } catch (Exception ex) {
        }
    }

}
