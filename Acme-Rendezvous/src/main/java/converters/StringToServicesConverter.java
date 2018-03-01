package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.ServicesRepository;
import domain.Services;

@Component
@Transactional
public class StringToServicesConverter implements Converter<String, Services>{
	
	@Autowired
	ServicesRepository	servicesRepository;


	@Override
	public Services convert(final String text) {
		Services result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.servicesRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
