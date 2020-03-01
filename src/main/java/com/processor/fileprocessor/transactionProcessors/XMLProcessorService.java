package com.processor.fileprocessor.transactionProcessors;

import com.processor.fileprocessor.transactionHelperServices.TransactionValidatorService;
import com.processor.fileprocessor.transactionHelperServices.TransactionBlanceCheckerService;
import com.processor.fileprocessor.dto.Transaction;
import com.processor.fileprocessor.dto.Violation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class XMLProcessorService implements TransactionProcessor {
    @Autowired
    private TransactionValidatorService validatorService;

    @Autowired
    private TransactionBlanceCheckerService checkerService;

    private List<Transaction> transactionList = new ArrayList<>();

    @Override
    public void importTransactions(InputStream inputStream) {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);
            while (xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                    if (startElement.getName().getLocalPart().equalsIgnoreCase("Transaction")) {
                        Transaction transaction = new Transaction();
                        populateType(startElement, transaction);
                        populateAmount(startElement, transaction);
                        populateNarration(startElement, transaction);
                        this.transactionList.add(transaction);
                    }
                }
            }
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Unable to Parse XML file", e);
        }
    }

    private void populateNarration(StartElement startElement, Transaction transaction) {
        if (null != startElement.getAttributeByName(new QName("narration"))) {
            String narrationAtr = startElement.getAttributeByName(new QName("narration")).getValue();
            transaction.setNarration(narrationAtr);
        }
    }

    private void populateAmount(StartElement startElement, Transaction transaction) {
        if (null != startElement.getAttributeByName(new QName("amount"))) {
            String amtAtr = startElement.getAttributeByName(new QName("amount")).getValue();
            if (!amtAtr.isEmpty()) {
                transaction.setAmount(BigDecimal.valueOf(Double.valueOf(amtAtr)));
            }
        }
    }

    private void populateType(StartElement startElement, Transaction transaction) {
        if (null != startElement.getAttributeByName(new QName("type"))) {
            String typeAtr = startElement.getAttributeByName(new QName("type")).getValue();
            transaction.setType(typeAtr);
        }
    }

    @Override
    public List<Transaction> getImportedTransactions() {
        return this.transactionList;
    }

    @Override
    public List<Violation> validate() {
        return validatorService.validateTransactionsAndReutrnViolationsIfAny(getImportedTransactions());
    }

    @Override
    public Boolean isBalanced() {
        return checkerService.checkIfImportedTransactionsAreBalanced(getImportedTransactions());
    }


}
