/*
 * Copyright (c) 2011, Kevin Barroga
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Kevin Barroga nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY Kevin Barroga ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL Kevin Barroga BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.util.Iterator;

public abstract class GraphADT {
	
	//Accessor Methods
	public abstract int numVertices();
	
	public abstract int numArcs();
	
	public abstract Iterator<?> vertices();
	
	public abstract Iterator<?> arcs();
	
	public abstract Vertex<?, ?> getVertex(Object key);
	
	public abstract Arc<?, ?> getArc(Object source, Object target);
	
	public abstract Object getVertexData(Vertex<?, ?> v);
	
	public abstract Object getArcData(Arc<?, ?> a);
		
	public abstract int inDegree(Vertex<?, ?> v);
	
	public abstract int outDegree(Vertex<?, ?> v);
	
	public abstract Iterator<?> inAdjacentVertices(Vertex<?, ?> v);
	
	public abstract Iterator<?> outAdjacentVertices(Vertex<?, ?> v);
	
	//public abstract Iterator inIncidentArcs(Vertex v);
	
	//public abstract Iterator outIncidentArcs(Vertex v);
	
	public abstract Vertex<?, ?> origin(Arc<?, ?> a);
	
	public abstract Vertex<?, ?> destination(Arc<?, ?> a);
	
	//Mutator Methods
	public abstract Vertex<?, ?> insertVertex(Object key);	
	public abstract Vertex<?, ?> insertVertex(Object key, Object data);
	
	public abstract Arc<?, ?> insertArc(Vertex<?, ?> u, Vertex<?, ?> v);
	public abstract Arc<?, ?> insertArc(Vertex<?, ?> u, Vertex<?, ?> v, Object data);
	
	public abstract void setVertexData(Vertex<?, ?> v, Object data);
	
	public abstract void setArcData(Arc<?, ?> a, Object data);
	
	public abstract Object removeVertex(Vertex<?, ?> v);
	
	public abstract Object removeArc(Arc<?, ?> a);
	
	//public abstract void setDirectionFrom(Arc a, Vertex v);
	
	//public abstract void setDirectionTo(Arc a, Vertex v);
	
	public abstract void reverseDirection(Arc<?, ?> a);
	
	//Annotators
	public abstract void setAnnotation(Vertex<?, ?> v, Object k, Object o);
	public abstract void setAnnotation(Arc<?, ?> a, Object k, Object o);
	
	public abstract Object getAnnotation(Vertex<?, ?> v, Object k);
	public abstract Object getAnnotation(Arc<?, ?> a, Object k);
	
	public abstract Object removeAnnotation(Vertex<?, ?> v, Object k);
	public abstract Object removeAnnotation(Arc<?, ?> a, Object k);
	
	public abstract void clearAnnotations(Object k);

}
