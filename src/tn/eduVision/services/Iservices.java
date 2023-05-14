/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.services;

import java.util.List;

/**
 *
 * @author job_j
 */
public interface Iservices<T> {
    void add(T item);
    void update(T item);
    void delete(T item);
    T getById(int id);
    List<T> getAll();
}
