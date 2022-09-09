package com.sofkau.retofinal.interfaces;

import com.sofkau.retofinal.models.DetallesDeCorreo;

public interface IEmailService {
    String sendSimpleMail(DetallesDeCorreo details);
}
