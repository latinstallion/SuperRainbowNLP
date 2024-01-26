package rainbownlp.util.caching;

import java.util.HashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import rainbownlp.util.HibernateUtil;

@Entity
@Table( name = "CacheEntry" )
/**
 * This is a general class to store key/value
 * @author Ehsan
 *
 */
public class CacheEntry {