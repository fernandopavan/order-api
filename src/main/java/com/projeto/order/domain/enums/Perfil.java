package com.projeto.order.domain.enums;

public enum Perfil {

    ADMIN(0, "ROLE_ADMIN"),
    PESSOA_FISICA(1, "ROLE_PESSOA_FISICA");

    private int id;
    private String descricao;

    Perfil(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public String getDescricao () {
        return descricao;
    }

    public static Perfil toEnum(Integer id) {

        if (id == null) {
            return null;
        }

        for (Perfil x : Perfil.values()) {
            if (id.equals(x.getId())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Id inválido: " + id);
    }


}
