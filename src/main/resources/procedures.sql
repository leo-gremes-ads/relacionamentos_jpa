-- =============================================
-- DROPAR AS PROCEDURES PARA RECRIAR
-- =============================================

DROP PROCEDURE IF EXISTS listarProdutosPorIngrediente;&&
DROP PROCEDURE IF EXISTS registrar_delete;&&
DROP TRIGGER IF EXISTS deletar_ingrediente;&&
DROP TRIGGER IF EXISTS deletar_produto;&&
DROP TRIGGER IF EXISTS deletar_ingrediente;&&

-- =============================================
-- LISTAR PRODUTOS POR INGREDIENTE
-- =============================================

CREATE PROCEDURE listarProdutosPorIngrediente(
    IN nome_ingrediente VARCHAR(255)
)
BEGIN
    SELECT p.produto_id, p.categoria, p.nome, p.preco
    FROM produto p
    INNER JOIN ingrediente_produto ip
        ON p.produto_id = ip.produto_id
    INNER JOIN ingrediente i
        ON i.id = ip.ingrediente_id AND i.nome = nome_ingrediente;
END&&

-- =============================================
-- GUARDAR NA TABELA LOG_DELETES
-- =============================================

CREATE PROCEDURE registrar_delete(
    IN p_tabela VARCHAR(255),
    IN p_registro_id VARCHAR(255)
)
BEGIN
    INSERT INTO log_deletes(tabela, registro_id, usuario, data_exclusao)
    VALUES (p_tabela, p_registro_id, CURRENT_USER(), NOW());
END&&

-- =============================================
-- TRIGGERS
-- =============================================

CREATE TRIGGER deletar_ingrediente
AFTER DELETE ON ingrediente
FOR EACH ROW
BEGIN
    CALL registrar_delete('ingrediente', OLD.id);
END&&

CREATE TRIGGER deletar_produto
AFTER DELETE ON produto
FOR EACH ROW
BEGIN
    CALL registrar_delete('produto', OLD.produto_id);
END&&

CREATE TRIGGER deletar_pedido
AFTER DELETE ON pedido
FOR EACH ROW
BEGIN
    CALL registrar_delete('pedido', OLD.pedido_id);
END&&