package com.mycompany.proyecto_edd;

import java.util.Comparator;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class ComparadorCumpleanosProximo implements Comparator<Contacto> {
    private LocalDate hoy = LocalDate.now();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private long diasHastaCumple(Contacto c) {
        try {
            if (c.getFechaCumpleanos() == null || c.getFechaCumpleanos().isEmpty()) {
                return Long.MAX_VALUE; // Muy lejos si no tiene fecha
            }
            LocalDate cumple = LocalDate.parse(c.getFechaCumpleanos(), formatter);
            LocalDate cumpleEsteAno = cumple.withYear(hoy.getYear());
            if (cumpleEsteAno.isBefore(hoy) || cumpleEsteAno.isEqual(hoy)) {
                cumpleEsteAno = cumpleEsteAno.plusYears(1);
            }
            return ChronoUnit.DAYS.between(hoy, cumpleEsteAno);
        } catch (Exception e) {
            return Long.MAX_VALUE;
        }
    }

    @Override
    public int compare(Contacto c1, Contacto c2) {
        return Long.compare(diasHastaCumple(c1), diasHastaCumple(c2));
    }
}
