package com.capgemini.test.code.service;

import com.capgemini.test.code.clients.EmailClient;
import com.capgemini.test.code.clients.SmsClient;
import feign.FeignException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import com.capgemini.test.code.entity.Room;
import com.capgemini.test.code.entity.User;
import com.capgemini.test.code.repository.RoomRepository;
import com.capgemini.test.code.repository.UserRepository;

import com.capgemini.test.code.clients.DniClient;
import com.capgemini.test.code.clients.CheckDniRequest;

import java.util.Map;

@Service

public class Userservice {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final DniClient dniClient;
    private final EmailClient emailClient;
    private final SmsClient smsClient;


    // Constructor: dependencias
    public Userservice(RoomRepository roomRepository, UserRepository userRepository,  DniClient dniClient, EmailClient emailClient, SmsClient smsClient) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.dniClient = dniClient;
        this.emailClient = emailClient;
        this.smsClient = smsClient;
    }

    // Comprueba si existe un usuario por mail
    public boolean existeEmail(String email){
        return this.userRepository.existsByEmail(email);
    }

    // Busca sala por su identificador
    public Room buscarSala(Long id){
        return this.roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Sala no encontrada" + id));
    }

    // Metodo para Validar Datos de Usuario
    public User crearUsuario (User user){

        // Validar nombre
        if (user.getName() == null || user.getName().length()>6){
            throw new IllegalArgumentException("Nombre de usuario no valido");
        }
        // Validar mail
        if (user.getEmail()==null || ! user.getEmail().contains("@") || ! user.getEmail().contains(".")){
            throw new IllegalArgumentException("email");
        }

        // Comprobar si existe mail
        if (userRepository.existsByEmail(user.getEmail())){
            throw new IllegalArgumentException("email");
        }

        System.out.println ("ROL:" + user.getRole());
        // Validar rol
        if (!"admin".equals(user.getRole()) && ! "superadmin".equals(user.getRole()) ){
            throw new IllegalArgumentException("Rol no valido");
        }

        // Valida dni mediante API externa
        validarDni(user.getDni());

        // Buscar sala
        Room room = buscarSala(1L);

        // Asignar usuario a sala
        user.setRoom(room);

        // Guardar usuario en BD
        User usuarioGuardado = userRepository.save(user);
        enviarNotificacion(usuarioGuardado);

        return usuarioGuardado;

    }

    // Metodo Validar Dni
    private void validarDni(String dni){

        if(dni==null || dni.isBlank()) {
            throw new IllegalArgumentException("dni");
        }

        try{
            CheckDniRequest request = new CheckDniRequest(dni);
            dniClient.check(request);

        } catch (FeignException.Conflict ex){
                throw new IllegalArgumentException("dni",ex );
        }
    }

    private void enviarNotificacion (User user){

        String mensaje = "Usuario guardado";

        if ("admin".equals(user.getRole())){

            Map<String, String> request = Map.of("email", user.getEmail(), "message", mensaje);

            emailClient.enviarEmail(request);

        } else if ("superadmin".equals(user.getRole())){

            Map<String, String> request = Map.of("phone", user.getPhone(), "message", mensaje);

            smsClient.enviarSms(request);
        }
    }

    public User buscarUsuario (Long id){
        return userRepository.findByIdAndRoomId(id,1L).orElseThrow(() -> new RuntimeException("Usuario no encontrado" + id));
    }
}
