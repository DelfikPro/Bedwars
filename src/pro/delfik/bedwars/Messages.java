package pro.delfik.bedwars;

import pro.delfik.bedwars.util.CyclicIterator;

public enum Messages {
	YOU_BREAKED_OWN_BED(
			"Поздравляем вас, вы рашер номер 1 в мире. Вы сломали собственную кровать.",
			"Ай блядь, больно, хули ты творишь, я всего лишь кровать...",
			"Я своя, ниламай меня :c",
			"Я ТЕБЯ САМА СЕЙЧАС СЛОМАЮ СЛЫШЬ, ТЕСТИРОВЩИК ХРЕНОВ!",
			"Свою кровать ломать нельзя. А так хотелось...",
			"Не ломай свою кровать, она тебе ещё пригодится..."
	),

	GOT_STRIKED(
			"познал мощь великого Тора.",
			"получил разряд, не являясь шахматистом.",
			"был поджарен молнией.",
			"разгневал Зевса.",
			"потёр стекло об шерсть.",
			"засунул столовую вилку в розетку."
	)
	;

	private final CyclicIterator<String> messages;

	Messages(String... messages) {
		this.messages = new CyclicIterator<>(messages);
	}

	@Override
	public String toString() {
		return messages.next();
	}
}
