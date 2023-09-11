package BudgetTracker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean checkPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }
    public String getUsername()
    {
    	return username;
    }
}

class BudgetEntry {
    private Date date;
    private double amount;
    private String description;

    public BudgetEntry(Date date, double amount, String description) {
        this.date = date;
        this.amount = amount;
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }
}

public class BudgetTracker {
	private static Map<String, User> users = new HashMap<>();
	private static Map<String, List<BudgetEntry>> budgetEntries = new HashMap<>();

	public static void main(String[] args) {
		// Initializing some example data
		users.put("saikumar", new User("saikumar", "password123"));
		//users.put("saikumar", new User("saikumar", "password456"));

		List<BudgetEntry> saikumarsBudget = new ArrayList<>();
		saikumarsBudget.add(new BudgetEntry(new Date(), 500.0, "Groceries"));
		saikumarsBudget.add(new BudgetEntry(new Date(), 100.0, "Transportation"));
		budgetEntries.put("saikumar", saikumarsBudget);

		//List<BudgetEntry> saikumarsBudget = new ArrayList<>();
		saikumarsBudget.add(new BudgetEntry(new Date(), 700.0, "Rent"));
		saikumarsBudget.add(new BudgetEntry(new Date(), 50.0, "Entertainment"));
		budgetEntries.put("saikumar", saikumarsBudget);

		// Simulating user login
		User currentUser = login("saikumar", "password123");

		if (currentUser != null) {
			// Simulating budget entry addition
			BudgetEntry newEntry = new BudgetEntry(new Date(), 50.0, "Dining Out");
			addBudgetEntry(currentUser.getUsername(), newEntry);

			// Getting budget entries for a specific month
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
			List<BudgetEntry> septemberEntries = getBudgetEntriesForMonth(currentUser.getUsername(), calendar.getTime());

			// Displaying budget entries
			for (BudgetEntry entry : septemberEntries) {
				System.out.println("Date: " + entry.getDate() +
						", Amount: " + entry.getAmount() +
						", Description: " + entry.getDescription());
			}
		}
	}

	public static User login(String username, String password) {
		User user = users.get(username);

		if (user != null && user.checkPassword(password)) {
			System.out.println("Login successful. Welcome, " + user.getUsername() + "!");
			return user;
		} else {
			System.out.println("Invalid credentials. Please try again.");
			return null;
		}
	}

	public static void addBudgetEntry(String username, BudgetEntry entry) {
		List<BudgetEntry> userBudget = budgetEntries.get(username);

		if (userBudget != null) {
			userBudget.add(entry);
			System.out.println("Budget entry added successfully.");
		} else {
			System.out.println("User not found.");
		}
	}

	public static List<BudgetEntry> getBudgetEntriesForMonth(String username, Date month) {
		List<BudgetEntry> userBudget = budgetEntries.get(username);
		List<BudgetEntry> entriesForMonth = new ArrayList<>();

		if (userBudget != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(month);

			int desiredMonth = calendar.get(Calendar.MONTH);
			int desiredYear = calendar.get(Calendar.YEAR);

			for (BudgetEntry entry : userBudget) {
				calendar.setTime(entry.getDate());

				int entryMonth = calendar.get(Calendar.MONTH);
				int entryYear = calendar.get(Calendar.YEAR);

				if (entryMonth == desiredMonth && entryYear == desiredYear) {
					entriesForMonth.add(entry);
				}
			}
		}

		return entriesForMonth;
	}
}