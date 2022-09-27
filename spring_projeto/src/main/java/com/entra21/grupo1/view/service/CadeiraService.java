package com.entra21.grupo1.view.service;
import com.entra21.grupo1.model.dto.*;
import com.entra21.grupo1.model.entity.CadeiraEntity;
import com.entra21.grupo1.model.entity.CinemaEntity;
import com.entra21.grupo1.model.entity.PessoaEntity;
import com.entra21.grupo1.model.entity.SalaEntity;
import com.entra21.grupo1.view.repository.CadeiraRepository;
import com.entra21.grupo1.view.repository.SalaRepository;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class CadeiraService {

    @Autowired
    private CadeiraRepository cadeiraRepository;

    @Autowired
    private SalaRepository salaRepository;

    /**Busca todas as cadeiras do banco de dados.
     * @return List<CadeiraDTO> - Retorna uma lista de DTO de todas as cadeiras existentes.
     */
    public List<CadeiraDTO> getAll(){
        return cadeiraRepository.findAll().stream().map( cadeira -> {
            CadeiraDTO cadeiraDTO= new CadeiraDTO();
            cadeiraDTO.setId(cadeira.getId());
            cadeiraDTO.setTipoCadeira(cadeira.getTipoCadeira());
            cadeiraDTO.setCodigo(cadeira.getCodigo());
            cadeiraDTO.setFileira(cadeira.getFileira());
            cadeiraDTO.setOrdemFileira(cadeira.getOrdemFileira());
            return cadeiraDTO;
        }).collect(Collectors.toList());
    }

    /**Adiciona cadeira ao banco de dados.
     * @param input CadeiraPayloadDTO - Dados de uma nova cadeira.
     */
    public void saveCadeira(@NotNull CadeiraPayloadDTO input) {
        CadeiraEntity newCadeira = new CadeiraEntity();
        newCadeira.setCodigo(input.getCodigo());
        newCadeira.setTipoCadeira(input.getTipoCadeira());
        newCadeira.setFileira(input.getFileira());
        newCadeira.setOrdemFileira(input.getOrdemFileira());
        SalaEntity salaEntity = salaRepository.findById(input.getIdSala()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sala não encontrada!"));
        newCadeira.setSala(salaEntity);
        cadeiraRepository.save(newCadeira);
    }

    /**Atualiza cadeira existente do banco de dados.
     * @param newCadeira CadeiraDTO - Dados de uma cadeira que será atualizada.
     * @return CadeiraDTO - Dados atualizados da cadeira.
     */
    public CadeiraDTO update(@NotNull CadeiraDTO newCadeira) {
        CadeiraEntity e = cadeiraRepository.findById(newCadeira.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cadeira não encontrada!"));
        if(newCadeira.getCodigo() != null) e.setCodigo(newCadeira.getCodigo());
        if(newCadeira.getTipoCadeira() != null) e.setTipoCadeira(newCadeira.getTipoCadeira());
        if(newCadeira.getFileira() != null) e.setFileira(newCadeira.getFileira());
        if(newCadeira.getOrdemFileira() != null) e.setOrdemFileira(newCadeira.getOrdemFileira());
        cadeiraRepository.save(e);
        return newCadeira;
    }

    /**Deleta cadeira do banco de dados.
     * @param id Long - Identificador de uma cadeira existente.
     */
    public void delete(@NotNull Long id) {cadeiraRepository.deleteById(id);}
}
