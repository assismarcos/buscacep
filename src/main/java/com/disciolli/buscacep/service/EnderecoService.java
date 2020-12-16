package com.disciolli.buscacep.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.disciolli.buscacep.model.Endereco;
import com.disciolli.buscacep.repository.EnderecoRepository;

@Service
public class EnderecoService {

	private EnderecoRepository enderecoRepository;
	
	public EnderecoService(EnderecoRepository enderecoRepository) {
		this.enderecoRepository = enderecoRepository;
	}

	public Optional<Endereco> buscarEnderecoPorCep(String cep) {
		Optional<Endereco> end = enderecoRepository.findById(cep);

		if (end.isEmpty()) {

			List<String> cepsAprox = obterCepsAprox(cep);

			for (String cepAprox : cepsAprox) {
				end = enderecoRepository.findById(cepAprox);

				if (end.isPresent())
					return end;
			}

		}

		return end;
	}

	private List<String> obterCepsAprox(String cep) {
		List<String> ret = new ArrayList<String>();

		char[] chars = cep.toCharArray();

		for (int i = cep.length() - 1; i > 0; i--) {
			chars[i] = '0';
			ret.add(String.valueOf(chars));
		}

		return ret;
	}


}
