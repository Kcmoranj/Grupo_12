package com.mycompany.proyecto_edd;

import java.util.Comparator;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class ComparadorCumpleanosProximo implements Comparator<Contacto> {
    private LocalDate hoy = LocalDate.now();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");

    @Override
    public int compare(Contacto c1, Contacto c2) {
        long diasC1 = diasHastaCumple(c1);
        long diasC2 = diasHastaCumple(c2);
        return Long.compare(diasC1, diasC2);
    }

    private long diasHastaCumple(Contacto c) {
        try {
            if (c.getFechaCumpleanos() == null ||  c.getFechaCumpleanos().isEmpty()) {
                return Long.MAX_VALUE; // Si no tiene fecha, se considera muy lejos
            }

            String fechaConAno = c.getFechaCumpleanos() + "-" + hoy.getYear(); // Añadir el año al final
            LocalDate cumple = LocalDate.parse(fechaConAno, DateTimeFormatter.ofPattern("MM-dd-yyyy"));

            // Ajustar el cumpleaños al año actual o al próximo año
            LocalDate cumpleEsteAno = cumple.withYear(hoy.getYear());

            // Si el cumpleaños ya pasó este año o es el mismo día, ajustamos al próximo año
            if (cumpleEsteAno.isBefore(hoy) ||  cumpleEsteAno.isEqual(hoy)) {
                cumpleEsteAno = cumpleEsteAno.plusYears(1);  // Si el cumpleaños ya pasó, ir al siguiente año
            }

            // Verificar la diferencia en días
            return ChronoUnit.DAYS.between(hoy, cumpleEsteAno);
        } catch (Exception e) {
            System.out.println("Error al procesar la fecha de cumpleaños: " + c.getFechaCumpleanos());
            e.printStackTrace();
            return Long.MAX_VALUE;
        }
    }
}
