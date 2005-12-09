package org.nakedobjects.distribution.dummy;

import org.nakedobjects.distribution.ReferenceData;
import org.nakedobjects.object.Oid;
import org.nakedobjects.object.Version;
import org.nakedobjects.utility.ToString;


public class DummyReferenceData implements ReferenceData {
    private final Oid oid;
    private final String type;
    private final Version version;

    public DummyReferenceData(final Oid oid, final String type, final Version version) {
        this.oid = oid;
        this.type = type;
        this.version = version;
    }

    public Oid getOid() {
        return oid;
    }

    public String getType() {
        return type;
    }

    public Version getVersion() {
        return version;
    }

    public String toString() {
        ToString str = new ToString(this);
        toString(str);
        return str.toString();
    }

    protected void toString(ToString str) {
        str.append("oid", oid);
        str.append("type", type);
        str.append("version", version);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj instanceof DummyReferenceData) {
            DummyReferenceData ref = (DummyReferenceData) obj;
            return (oid == null ? ref.oid == null : oid.equals(ref.oid))
                    && (type == null ? ref.type == null : type.equals(ref.type))
                    && (version == null ? ref.version == null : version.equals(ref.version));
        }
        return false;
    }
}

/*
 * Naked Objects - a framework that exposes behaviourally complete business objects directly to the user.
 * Copyright (C) 2000 - 2005 Naked Objects Group Ltd
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General
 * Public License as published by the Free Software Foundation; either version 2 of the License, or (at your
 * option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program; if not, write to
 * the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * The authors can be contacted via www.nakedobjects.org (the registered address of Naked Objects Group is
 * Kingsway House, 123 Goldworth Road, Woking GU21 1NR, UK).
 */