CREATE TABLE lancamento (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	descricao VARCHAR(50) NOT NULL,
	data_vencimento DATE NOT NULL,
	data_pagamento DATE,
	valor DECIMAL(10,2) NOT NULL,
	observacao VARCHAR(100),
	tipo VARCHAR(20) NOT NULL,
	categoria_id BIGINT(20) NOT NULL,
	pessoa_id BIGINT(20) NOT NULL,
	FOREIGN KEY (categoria_id) REFERENCES categoria(id),
	FOREIGN KEY (pessoa_id) REFERENCES pessoa(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;