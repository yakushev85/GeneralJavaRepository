package org.yakushev.shopwebapp.service;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yakushev.shopwebapp.model.Transaction;
import org.yakushev.shopwebapp.repository.TransactionRepository;
import org.yakushev.shopwebapp.security.JwtTokenRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private JwtTokenRepository jwtTokenRepository;

	@Override
	public List<Transaction> getAll() {
		return Lists.newArrayList(transactionRepository.findAll());
	}

	@Override
	public Transaction getById(Long id) {
		return transactionRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Transaction add(Transaction value) {
		value.setId(null);
		value.setCreatedAt(new Date());
		return transactionRepository.save(value);
	}

	@Override
	@Transactional
	public Transaction update(Transaction value) {
		Transaction oldValue = getById(value.getId());
		try {
			Transaction updatedValue = (new Transaction()).merge(getById(value.getId()), value);
			return transactionRepository.save(updatedValue);
		} catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
			return oldValue;
		}
	}

}