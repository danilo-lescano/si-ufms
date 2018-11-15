/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;
import java.util.Objects;
import javax.ejb.CreateException;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author 201319070221
 */
@Stateless
public class UsuarioManager implements UsuarioManagerLocal {
    @EJB
    private UsuarioFacadeLocal usuarioFacade;
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Override
    public Usuario find(Long id) throws CreateException{
        Usuario u = usuarioFacade.find(id);
        
        if(u != null){
            return u;
        }else{
            throw new CreateException("Usuário não encontrado");
        }
    }
    
    @Override
    public boolean criaUsuario(String nome, String login, String senha) throws CreateException{
        Usuario u = usuarioFacade.findByLogin(login);
        
        if(u == null){
            u = new Usuario(nome, login, senha);
            usuarioFacade.create(u);
            return true;
        }else{
            throw new CreateException("Login já cadastrado");
        }
    }

    @Override
    public boolean editaUsuario(Long id, String nome, String login, String senha) throws CreateException{
        Usuario u = usuarioFacade.findByLogin(login);
        if(u != null && !Objects.equals(u.getId(), id)){
            throw new CreateException("Login já cadastrado");
        }
        u = usuarioFacade.find(id);
        if(u != null){
            u.setLogin(login);
            u.setNome(nome);
            u.setSenha(senha);

            usuarioFacade.edit(u);
            return true;
        }else{
            throw new CreateException("Login já cadastrado");
        }
    }
   
    @Override
    public boolean excluiUsuario(Long id) throws CreateException{
        Usuario u = usuarioFacade.find(id);
        
        if(u != null){
            usuarioFacade.remove(u);
            return true;
        }else{
            throw new CreateException("ID não encontrado cadastrado");
        }
    }
    
    @Override
    public List<Usuario> listUsuarios(){
        return usuarioFacade.findAll();
    }
}