package br.com.grupo06.wishlist.domain.entity;

public class ClienteBuilder {

    public ClienteEntity defaultValues(){
        return new ClienteEntity(ClienteDefaultValues.NOME,
                ClienteDefaultValues.CPF,
                ClienteDefaultValues.TELEFONE,
                ClienteDefaultValues.EMAIL,
                ClienteDefaultValues.LOGRADOURO,
                ClienteDefaultValues.NUMERO,
                ClienteDefaultValues.BAIRRO,
                ClienteDefaultValues.COMPLEMENTO,
                ClienteDefaultValues.CIDADE,
                ClienteDefaultValues.ESTADO,
                ClienteDefaultValues.CEP); }

}
