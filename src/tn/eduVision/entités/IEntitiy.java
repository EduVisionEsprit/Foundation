/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.eduVision.entités;

import java.util.UUID;

/**
 *
 * @author job_j
 */
public interface IEntitiy {
    void SetId(UUID id);
    UUID GetId();
}
