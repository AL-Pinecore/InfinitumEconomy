package cn.infinitumstudios.infinitumEconomy.foundation.database;

import cn.infinitumstudios.infinitumEconomy.foundation.types.Loan;

public class LoanDatabase extends Database<Loan>{
    public LoanDatabase(String fileName, Class<Loan> classOfT) {
        super(fileName, classOfT);
    }

    public Loan newLoan(Loan loan) {
        this.create(loan);
        return loan;
    }
}
