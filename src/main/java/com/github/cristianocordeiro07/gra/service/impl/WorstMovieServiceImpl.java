package com.github.cristianocordeiro07.gra.service.impl;

import com.github.cristianocordeiro07.gra.model.Producer;
import com.github.cristianocordeiro07.gra.model.WorstMovie;
import com.github.cristianocordeiro07.gra.model.dto.ProducerAwardsIntervalDTO;
import com.github.cristianocordeiro07.gra.repository.WorstMovieRepository;
import com.github.cristianocordeiro07.gra.service.WorstMovieService;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class WorstMovieServiceImpl implements WorstMovieService {

    Logger logger = LoggerFactory.getLogger(WorstMovieServiceImpl.class);

    private final WorstMovieRepository repository;

    private final Environment env;

    WorstMovieServiceImpl(WorstMovieRepository repository, Environment env) {
        Objects.requireNonNull(repository);
        Objects.requireNonNull(env);
        this.repository = repository;
        this.env = env;
    }

    public final void createWorstMovie(final WorstMovie worstMovie) {
        repository.save(worstMovie);
    }

    /**
     * Retorna o produtor com maior intervalo entre prêmios consecutivos e o produtor com menor intervalo entre os prêmios
     */
    public ProducerAwardsIntervalDTO getProducerAwardsInverval() {
        return new ProducerAwardsIntervalDTO(
                repository.getProducerAwardsMinInterval(), repository.getProducerAwardsMaxInterval()
        );
    }

    /**
     * Carrega todos os filtros de um arquivo .csv e para o banco de dados
     */
    private void loadDatabaseFromCsvFile() {

        final String fileName = env.getProperty("app.worstMovies.filePath");
        logger.info("Realizando carga do arquivo: " + fileName);

        CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();
        try (CSVReader reader = new CSVReaderBuilder(
                new FileReader(fileName))
                //custom parser
                .withCSVParser(csvParser)
                .build()) {

            final List<String[]> readerObjects = reader.readAll();
            if (readerObjects.size() > 0) {

                //Varre todas as linhas, ignorando a linha 0 (cabeçalho)
                for (int i = 1; i < readerObjects.size(); i++) {

                    final String[] readerObject = readerObjects.get(i);

                    //Criação de um registro
                    WorstMovie worstMovie = new WorstMovie();
                    worstMovie.setId(i);
                    worstMovie.setYear(Integer.parseInt(readerObject[0]));
                    worstMovie.setTitle(readerObject[1]);
                    worstMovie.setStudios(readerObject[2]);

                    //O filme pode ter vários produtores.
                    //Conforme o arquivo CSV, produtores estão separados por diversos padrões:
                    //', '
                    //' and '
                    //', and '
                    String producers = readerObject[3];

                    //1- padronizamos para separar todos por ','
                    producers = producers.replace(", and ", ", ");
                    producers = producers.replace(" and ", ", ");
                    producers = producers.replace(", ", ",");

                    //2- adicionamos tudo na lista de produtores
                    List<String> producerList = new ArrayList<>(Arrays.asList(producers.split(",")));

                    //3- Criamos a lista de Produtores e setamos na entidade WorstMovie
                    List<Producer> producerEntityList = new ArrayList<>();
                    producerList.forEach(producer -> {
                        Producer newProducer = new Producer(producer);
                        producerEntityList.add(newProducer);
                    });
                    worstMovie.setProducers(producerEntityList);
                    worstMovie.setWinner(readerObject[4] != null && readerObject[4].equals("yes"));

                    //Salvando o registro
                    createWorstMovie(worstMovie);
                }
                logger.info("Registros de filme carregados: " + (readerObjects.size() - 1));
            } else {
                logger.info("Nenhum registro de filme carregado");
            }
        } catch (FileNotFoundException e) {
            logger.error("O arquivo .csv não foi encontrado: " + fileName, e);
        } catch (IOException | CsvException e) {
            logger.error("Problema ao abrir o arquivo .csv: " + fileName, e);
        }
    }

    @SuppressWarnings("unused")
    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadDatabaseFromCsvFile();
    }
}
