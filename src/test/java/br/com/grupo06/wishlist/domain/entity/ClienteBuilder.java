package br.com.grupo06.wishlist.domain.entity;

// Classe para construcao e inicializacao de objetos Clientes
public class ClienteBuilder {

    public ClienteEntity defaultValues(Integer index){
        return new ClienteEntity(ClienteDefaultValues.NOME[index],
                ClienteDefaultValues.CPF[index],
                ClienteDefaultValues.TELEFONE[index],
                ClienteDefaultValues.EMAIL[index],
                ClienteDefaultValues.LOGRADOURO[index],
                ClienteDefaultValues.NUMERO[index],
                ClienteDefaultValues.BAIRRO[index],
                ClienteDefaultValues.COMPLEMENTO[index],
                ClienteDefaultValues.CIDADE[index],
                ClienteDefaultValues.ESTADO[index],
                ClienteDefaultValues.CEP[index]);
    }
}
