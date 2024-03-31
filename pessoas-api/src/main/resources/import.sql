INSERT INTO tb_pessoa (nome_completo, data_nascimento) VALUES ('Samuel Ferreira', '1985-08-27');
INSERT INTO tb_pessoa (nome_completo, data_nascimento) VALUES ('Naruto Uzumaki', '1990-04-02');

INSERT INTO tb_endereco (logradouro, cep, numero, cidade, estado, principal, pessoa_id) VALUES ('Maria Ventura', '62840-000', '069', 'Beberibe', 'Ceará', TRUE, 1);
INSERT INTO tb_endereco (logradouro, cep, numero, cidade, estado, principal, pessoa_id) VALUES ('Minato', '77777-000', '500', 'Vila da Folha', 'Konoha', TRUE, 2);
INSERT INTO tb_endereco (logradouro, cep, numero, cidade, estado, principal, pessoa_id) VALUES ('Manuel Machado', '61880-000', '342', 'Itaitinga', 'Ceará', FALSE, 1);