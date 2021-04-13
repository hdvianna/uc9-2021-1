package lecture11;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BrandReportPager implements AutoCloseable {

    private ResultSet resultSet;
    private PreparedStatement statement;
    private int pageSize;
    private int page = 0;

    public BrandReportPager(Connection connection, int pageSize) throws SQLException {
        this.pageSize = pageSize;
        String sql = "SELECT\n" +
                "  Marca.descricao brand,\n" +
                "  Tipo.descricao type,\n" +
                "  Tamanho.sigla size,\n" +
                "  Cor.descricao color,\n" +
                "  preco price,\n" +
                "  estoque stock,\n" +
                "  AVG(preco) OVER (PARTITION BY id_marca, id_tipo) stock_avg\n" +
                "FROM\n" +
                "  Catalogo \n" +
                "  \tJOIN Marca ON Catalogo.id_marca = Marca.id\n" +
                "  \tJOIN Tipo ON Catalogo.id_marca = Tipo.id\n" +
                "    JOIN Tamanho ON Catalogo.id_marca = Tamanho.id\n" +
                "    JOIN Cor ON Catalogo.id_cor = Cor.id\n" +
                "LIMIT ? OFFSET ?";
        statement = connection.prepareStatement(sql);
        statement.setInt(1, this.pageSize);
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public int getPage() {
        return page;
    }

    public boolean next()  throws SQLException {
        /**
         * Codifique a paginação
         * O código que verifique se há próxima página está ok
         * Basta movimentar para a próxima página
         * Analise a função moveResultSetTo()
         */
        return resultSet.first();
    }

    private void moveResultSetTo(int page) throws SQLException {
        statement.setInt(2, ((page-1) * this.pageSize));
        resultSet = statement.executeQuery();
    }

    @Override
    public void close() throws IOException, SQLException {
        statement.close();
        resultSet.close();
    }
}
