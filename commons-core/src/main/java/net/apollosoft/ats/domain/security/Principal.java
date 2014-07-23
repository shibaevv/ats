package net.apollosoft.ats.domain.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.apollosoft.ats.util.Pair;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class Principal implements Serializable {

    /** serialVersionUID */
	private static final long serialVersionUID = -7243902282972950325L;

	private final Pair<String> user;

    private final IRole[] roles;

    private final List<GrantedAuthority> grantedAuthorities;

    public Principal(Pair<String> user, List<? extends IRole> roles)
    {
        this.user = user;
        this.roles = (IRole[]) roles.toArray(new IRole[0]);
        // add GrantedAuthority(s)
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        for (IRole item : this.roles)
        {
            GrantedAuthority ga = new GrantedAuthorityImpl("ROLE_" + item.getId());
            if (!grantedAuthorities.contains(ga))
            {
                grantedAuthorities.add(ga);
            }
        }
        this.grantedAuthorities = grantedAuthorities;
    }

    /**
     * @return the userId
     */
    public String getUserId()
    {
        return user == null ? null : user.getId();
    }

    /**
     * @return the username
     */
    public String getUsername()
    {
        return user == null ? null : user.getName();
    }

    /**
     * @return the roles
     */
    public IRole[] getRoles()
    {
        return roles;
    }

    /**
     * @return the grantedAuthorities
     */
    public List<GrantedAuthority> getGrantedAuthority()
    {
        return grantedAuthorities;
    }

}