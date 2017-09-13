package sample;

import java.math.BigDecimal;
import java.util.Date;

public class SampleDetailsVO {

	private String type;
	private String id;
	private String name;
	private String mode;
	private Date paymentDate;
	private Date dueDate;
	private BigDecimal fine;
	private BigDecimal amount;

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(final String mode) {
		this.mode = mode;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(final Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(final Date dueDate) {
		this.dueDate = dueDate;
	}

	public BigDecimal getFine() {
		return fine;
	}

	public void setFine(final BigDecimal fine) {
		this.fine = fine;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(final BigDecimal amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "[Id = " + id + "] [Type = " + type + "] [Name = " + name + "] [Mode = " + mode + "] [Payment Date = "
				+ paymentDate + "] [Due Date = " + dueDate + "] [Amount = " + amount + "]";
	}

}
