package io.ylab.intensive.lesson04.movie;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class MovieLoaderImpl implements MovieLoader {
    private DataSource dataSource;
    private static final int COUNT_LINES_TO_SKIP = 2;
    private List<Movie> listMovie;

    public MovieLoaderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        listMovie = new ArrayList<>();
    }

    @Override
    public void loadData(File file) throws Exception {
        // РЕАЛИЗАЦИЮ ПИШЕМ ТУТ
        setListMovieFromFile(file);
        saveMovies();
    }

    private void setListMovieFromFile(File file) throws Exception{
        try (FileReader filereader = new FileReader(file);
             CSVReader csvReader = new CSVReaderBuilder(filereader)
                     .withCSVParser(new CSVParserBuilder().withSeparator('\n').build())
                     .withSkipLines(COUNT_LINES_TO_SKIP)
                     .build();
        ) {

            String[] nextLine;
            while ((nextLine = csvReader.readNext()) != null) {
                String[] dataMovie = nextLine[0].split(";");
                Movie movie = new Movie();

                if (!dataMovie[0].isEmpty()) {
                    movie.setYear(Integer.valueOf(dataMovie[0], 10));
                }

                if (!dataMovie[1].isEmpty()) {
                    movie.setLength(Integer.valueOf(dataMovie[1], 10));
                }

                movie.setTitle(dataMovie[2]);
                movie.setSubject(dataMovie[3]);
                movie.setActors(dataMovie[4]);
                movie.setActress(dataMovie[5]);
                movie.setDirector(dataMovie[6]);

                if (!dataMovie[7].isEmpty()) {
                    movie.setPopularity(Integer.valueOf(dataMovie[7], 10));
                }

                movie.setAwards((dataMovie[8].equals("Yes")));
                this.listMovie.add(movie);
            }
        }
    }

    private void saveMovies() throws SQLException {
        String insertSQL = "INSERT INTO movie (\"year\", length, title, subject, actors, actress, director, popularity, awards)" +
                " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = this.dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            for (Movie movie : this.listMovie) {
                setDataPrepareStatement(preparedStatement, 1, movie.getYear());
                setDataPrepareStatement(preparedStatement, 2, movie.getLength());
                setDataPrepareStatement(preparedStatement, 3, movie.getTitle());
                setDataPrepareStatement(preparedStatement, 4, movie.getSubject());
                setDataPrepareStatement(preparedStatement, 5, movie.getActors());
                setDataPrepareStatement(preparedStatement, 6, movie.getActress());
                setDataPrepareStatement(preparedStatement, 7, movie.getDirector());
                setDataPrepareStatement(preparedStatement, 8, movie.getPopularity());
                setDataPrepareStatement(preparedStatement, 9, movie.getAwards());
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        }

    }

    private void setDataPrepareStatement(PreparedStatement preparedStatement, int index, Integer value)
            throws SQLException {
        if (value == null) {
            preparedStatement.setNull(index, Types.INTEGER);
        } else {
            preparedStatement.setInt(index, value);
        }
    }

    private void setDataPrepareStatement(PreparedStatement preparedStatement, int index, String value)
            throws SQLException {
        if (value == null) {
            preparedStatement.setNull(index, Types.VARCHAR);
        } else {
            preparedStatement.setString(index, value);
        }
    }

    private void setDataPrepareStatement(PreparedStatement preparedStatement, int index, Boolean value)
            throws SQLException {
        if (value == null) {
            preparedStatement.setNull(index, Types.BOOLEAN);
        } else {
            preparedStatement.setBoolean(index, value);
        }
    }
}
