/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.entities;

import edu.eci.pdsw.samples.managebeans.RegistroConsultaBean;
import edu.eci.pdsw.samples.services.ExcepcionServiciosPacientes;
import edu.eci.pdsw.samples.services.ServiciosHistorialPacientesFactory;
import edu.eci.pdsw.samples.services.ServiciosPacientes;
import edu.eci.pdsw.samples.services.impl.ServiciosPacientesMock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author blackphantom
 */
@FacesConverter("epsConverter")
public class EpsConverter implements Converter {

    private Eps eps;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        try {
            ServiciosPacientes a = RegistroConsultaBean.servicepacientes;
            for (Eps q : a.obtenerEPSsRegistradas()) {
                if (q.getNombre().equals(string))
                    return q;
            }
        } catch (ExcepcionServiciosPacientes ex) {
            Logger.getLogger(EpsConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o instanceof Eps) {
            return ((Eps) o).getNombre();
        }
        return null;
    }

}
