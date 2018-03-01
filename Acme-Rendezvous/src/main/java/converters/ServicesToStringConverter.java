package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Services;

@Component
@Transactional
public class ServicesToStringConverter implements Converter<Services, String>{

	@Override
	public String convert(final Services services) {
		String result;

		if (services == null)
			result = null;
		else
			result = String.valueOf(services.getId());

		return result;
	}
}
