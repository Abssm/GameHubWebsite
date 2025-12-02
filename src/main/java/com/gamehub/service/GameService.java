package com.gamehub.service;

import com.gamehub.dto.GameDto;
import com.gamehub.entity.Game;
import com.gamehub.exception.GamehubException;
import com.gamehub.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameService {
    
    private final GameRepository gameRepository;
    private final ModelMapper modelMapper;
    
    public List<GameDto> getAllGames() {
        return gameRepository.findAll().stream()
                .map(game -> modelMapper.map(game, GameDto.class))
                .collect(Collectors.toList());
    }
    
    public List<GameDto> getGamesByCategory(String category) {
        return gameRepository.findByCategory(category).stream()
                .map(game -> modelMapper.map(game, GameDto.class))
                .collect(Collectors.toList());
    }
    
    public List<GameDto> getFeaturedGames() {
        return gameRepository.findByFeaturedTrue().stream()
                .map(game -> modelMapper.map(game, GameDto.class))
                .collect(Collectors.toList());
    }
    
    public List<GameDto> searchGames(String query) {
        return gameRepository.findByTitleContainingIgnoreCase(query).stream()
                .map(game -> modelMapper.map(game, GameDto.class))
                .collect(Collectors.toList());
    }
    
    public GameDto getGameById(Long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new GamehubException("Game not found"));
        return modelMapper.map(game, GameDto.class);
    }
    
    public GameDto addGame(GameDto gameDto) {
        Game game = modelMapper.map(gameDto, Game.class);
        game.setPriceSAR(game.getPriceUSD().multiply(BigDecimal.valueOf(3.75)));
        Game savedGame = gameRepository.save(game);
        return modelMapper.map(savedGame, GameDto.class);
    }
    
    public GameDto updateGame(Long id, GameDto gameDto) {
        Game existingGame = gameRepository.findById(id)
                .orElseThrow(() -> new GamehubException("Game not found"));
        
        modelMapper.map(gameDto, existingGame);
        Game updatedGame = gameRepository.save(existingGame);
        return modelMapper.map(updatedGame, GameDto.class);
    }
    
    public void deleteGame(Long id) {
        if (!gameRepository.existsById(id)) {
            throw new GamehubException("Game not found");
        }
        gameRepository.deleteById(id);
    }
    
    public Map<String, Integer> getCategoryCounts() {
        return gameRepository.countGamesByCategory().stream()
                .collect(Collectors.toMap(
                        obj -> (String) obj[0],
                        obj -> ((Long) obj[1]).intValue()
                ));
    }
    
    public List<GameDto> getTopSellingGames() {
        return gameRepository.findTopSellingGames().stream()
                .limit(10)
                .map(game -> modelMapper.map(game, GameDto.class))
                .collect(Collectors.toList());
    }
}