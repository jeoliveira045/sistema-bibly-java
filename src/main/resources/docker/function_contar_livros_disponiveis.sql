

CREATE OR REPLACE FUNCTION contar_livros_disponiveis(LIVROID NUMERIC)
RETURNS NUMERIC AS $$
DECLARE
    CONTAGEM NUMERIC;
    TOTAL_LIVROS NUMERIC;
    TOTAL_LIVROS_EMPRESTADOS NUMERIC;
BEGIN
    -- Seleciona o total de livros com o ISBN fornecido
    SELECT QUANTIA INTO TOTAL_LIVROS
    FROM LIVROQUANTIAESTOQUE
    WHERE LIVRO_ID = LIVROID;

    -- Seleciona o total de livros emprestados com o ISBN fornecido
    SELECT COUNT(EMP.LIVRO_ID) INTO TOTAL_LIVROS_EMPRESTADOS
    FROM EMPRESTIMOS EMP
    WHERE EMP.DATA_DEVOLUCAO IS NULL AND EMP.LIVRO_ID = LIVROID;

    -- Calcula a contagem de livros disponíveis
    CONTAGEM := TOTAL_LIVROS - TOTAL_LIVROS_EMPRESTADOS;

    -- Retorna a contagem
    RETURN CONTAGEM;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE VIEW LIVROS_DISPONIVEIS AS
SELECT DISTINCT
	ID,
	NOME,
	ISBN,
	CONTAR_LIVROS_DISPONIVEIS(ID) FROM LIVROS
GROUP BY NOME, ISBN, ID;

