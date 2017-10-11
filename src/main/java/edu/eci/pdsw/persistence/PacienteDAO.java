/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.persistence;

import edu.eci.pdsw.samples.entities.Paciente;
import java.util.List;
/**
 *
 * @author blackphantom
 */
public interface PacienteDAO {

    public List<Paciente> load() throws PersistenceException;

    public Paciente loadById(int id,String tipoId) throws PersistenceException;
    
    public void save(Paciente p) throws PersistenceException ;
    
    public void update(Paciente p) throws PersistenceException;
}
