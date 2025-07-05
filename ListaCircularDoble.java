
package Grupo_12;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListaCircularDoble<T> implements Iterable<T> {
    private class Nodo {
        T dato;
        Nodo anterior;
        Nodo siguiente;

        Nodo(T dato) {
            this.dato = dato;
        }
    }
    private Nodo cabeza = null;
    private int tamaño = 0;
    
    public void agregarAlFinal(T dato) {
        Nodo nuevo = new Nodo(dato);
        if (cabeza == null) {
            cabeza = nuevo;
            cabeza.anterior = cabeza;
            cabeza.siguiente = cabeza;
        } else {
            Nodo ultimo = cabeza.anterior;
            nuevo.anterior = ultimo;
            nuevo.siguiente = cabeza;
            ultimo.siguiente = nuevo;
            cabeza.anterior = nuevo;
        }
        tamaño++;
    }
    
    public T obtener(int indice) {
        if (indice < 0 || indice >= tamaño) {
            throw new IndexOutOfBoundsException("Índice inválido");
        }
        Nodo actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.siguiente;
        }
        return actual.dato;
    }
    public int tamaño() {
        return tamaño;
    }
    public boolean estaVacia() {
        return tamaño == 0;
    }
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Nodo actual = cabeza;
            private int contador = 0;

            @Override
            public boolean hasNext() {
                return contador < tamaño;
            }

            @Override
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                T dato = actual.dato;
                actual = actual.siguiente;
                contador++;
                return dato;
            }
        };
    }
    
    public void eliminar(int indice) {
        if (indice < 0 || indice >= tamaño) {
            throw new IndexOutOfBoundsException("Índice inválido");
        }
        Nodo actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.siguiente;
        }

        if (tamaño == 1) {
            cabeza = null;
        } else {
            actual.anterior.siguiente = actual.siguiente;
            actual.siguiente.anterior = actual.anterior;

            if (actual == cabeza) {
                cabeza = actual.siguiente;
            }
        }
        tamaño--;
    }
    public void clear() {
        cabeza = null;
        tamaño = 0;
    }
    public void ordenar(Comparator<T> comparador) {
        if (tamaño <= 1) return; // nada que ordenar
        boolean cambiado;
        do {
            cambiado = false;
            Nodo actual = cabeza;
            for (int i = 0; i < tamaño - 1; i++) {
                Nodo siguiente = actual.siguiente;
                if (comparador.compare(actual.dato, siguiente.dato) > 0) {
                    // Intercambiar datos (no nodos)
                    T temp = actual.dato;
                    actual.dato = siguiente.dato;
                    siguiente.dato = temp;
                    cambiado = true;
                }
                actual = actual.siguiente;
            }
        } while (cambiado);
    }
}
