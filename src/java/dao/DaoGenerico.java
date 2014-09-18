/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 *
 * @author Pablo Rocha
 */
public abstract class DaoGenerico<T, ID extends Serializable> {

    private Session sessao;

    public Session getSessao() {
        if (this.sessao == null || !this.sessao.isOpen()) {
            this.sessao = HibernateUtil.getSessionFactory().openSession();
        }
        return this.sessao;
    }

    public void salvar(Object entity) {
        try {
            this.iniciarTransacao();
            this.sessao.save(entity);
            this.commit();
        } catch (Exception e) {
            this.rollBack();
            e.printStackTrace();
        } finally {
            this.sessao.close();
        }
    }

    public void editar(Object entity) {
        try {
            this.iniciarTransacao();
            this.sessao.update(entity);
            this.commit();
        } catch (Exception e) {
            this.rollBack();
            e.printStackTrace();
        } finally {
            this.sessao.close();
        }
    }

    public void remover(Object entity) {
        try {
            this.iniciarTransacao();
            this.sessao.delete(entity);
            this.commit();
        } catch (Exception e) {
            this.rollBack();
            e.printStackTrace();
        } finally {
            this.sessao.close();
        }

    }

    public Object buscarPorId(Class<Object> classe, Serializable id) {
        try {
            this.iniciarTransacao();
            return (Object) this.sessao.load(classe, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sessao.close();
        }
        return null;
    }

    public List<Object> listarTudo(Class<Object> classe) {
        try {
            this.iniciarTransacao();
            return this.sessao.createCriteria(classe).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sessao.close();
        }
        return null;
    }

    public void iniciarTransacao() {
        this.sessao.beginTransaction();
    }

    public void fecharSessao() {
        this.sessao.close();
    }

    public void commit() {
        this.sessao.beginTransaction().commit();
    }

    public void rollBack() {
        this.sessao.beginTransaction().rollback();
    }
}
