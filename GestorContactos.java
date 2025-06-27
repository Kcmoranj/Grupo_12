package com.mycompany.proyecto_edd;

import java.util.LinkedList;

public class GestorContactos {
    private LinkedList<Contacto> contactos;
    
    //Constructor
    public GestorContactos() {
        contactos = new LinkedList<>();
    }
    
    public LinkedList<Contacto> getContactos(){
        return contactos;
    }
    
    public void agregarContacto(Contacto contacto){
        contactos.add(contacto);
    }
    
    public void mostrarContactos(){
        if(contactos.isEmpty()){
            System.out.println("No hay contactos");
            return;
        }
        for(int i=0;i<contactos.size();i++){
            System.out.println("Contacto: "+(i+1)+": "+contactos.get(i));            
        }
    }
      
}
