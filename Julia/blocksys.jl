#Przemyslaw Kolodziejczyk
#
#Modul z funkcjami dotyczacymi naszej macierzy rzadkiej

module blocksys

export addElement, readMatrixFromFile, readBvectorFromFile, writeXToFile, writeXToFileWithRelativeError, modifiedGauss, modifiedGaussPartialPivoting, countGauss, countGaussPartialPivoting, getLU, getLU_PartialPivoting, countGauss_withLU, countGaussPartialPivoting_withLU, max_in_column, swap, multMatrixAndVector, multMatrixAndVector_writeToFile, multMatrixAndOnes_writeToFile, getLU_fromFile, getLUPartialPivoting_fromFile, my_GaussianElimination, my_GaussianEliminationPartialPivoting, my_GaussianElimination_withLU, my_GaussianEliminationPartialPivoting_withLU

###########################################################################
###### OPERACJE NA PLIKACH ################################################
###########################################################################

#Dodanie elementu do wektorow, ktore beda wpisywane do macierzy.
function addElement(	XVector::Array{Int64}, YVector::Array{Int64}, ValueVector::Array{Float64},
			x::Int64, y::Int64, value::Float64)
	push!(XVector, x)
	push!(YVector, y)
	push!(ValueVector, value)
end

#funkcja czytajaca rozmiar macierzy n, rozmiar macieczy A B oraz C l,
#oraz cala macierz z pliku fileName
function readMatrixFromFile(fileName::AbstractString)
	matrix = SparseMatrixCSC{Float64,Int64}
	n = Int64
	l = Int64
	open(fileName) do f
		firstLine = split(readline(f), " ")
		n = parse(Int64, firstLine[1])
		l = parse(Int64, firstLine[2])
		xVector = Int64[]
		yVector = Int64[]
		valueVector = Float64[]
		i = 1
		for ln in eachline(f)
			ln = split(ln, " ")
			addElement(xVector, yVector, valueVector, parse(Int64, ln[1]), parse(Int64, ln[2]), parse(Float64, ln[3]))
		end
	matrix = sparse(xVector, yVector, valueVector)
	end
	return n, l, matrix
end

#Funkcja czytajaca rozmiar wektora b, oraz wartosci w jego komorkach z pliku
#o nazwie fileName
function readBvectorFromFile(fileName::AbstractString)
	f = open(fileName)
	n = parse(Int64, readline(f))
	b = Array(Float64, n)
	for i=1:n
		b[i] = parse(Float64, readline(f))	
	end
	close(f)
	return n, b
end

#Wpisanie do pliku o nazwie fileName calego wektora x
function writeXToFile(x, fileName::AbstractString)
	open(fileName, "w") do f
		for i=1:length(x)
			write(f, string(x[i]) * "\n")
		end
	end
end

#Wpisanie do pliku o nazwie file name calego wektora x, oraz blad wzgledny
#x oraz y
function writeXToFileWithRelativeError(x, y, fileName::AbstractString)
	open(fileName, "w") do f
		write(f, string(norm(y - x)/norm(y)) * "\n")
		for i=1:length(x)
			write(f, string(x[i]) * "\n")
		end
	end
end

###########################################################################
####### MODYFIKACJA MACIERZY DO MACIERZY TROJKATNEJ GORNEJ ################
###########################################################################

#Funkcja modyfikujaca macierz oraz wektor B w ten sposob ze zeruje komorki
#z macierzy trojkatnej dolnej, oraz aktualizuje wartosci poszczegolnych komorek.
#Funkcja robi to bez czesciowego wyboru elementu glownego.
function modifiedGauss(n::Int64, l::Int64, m, b)
	q = convert(Int64, n/l)
	for p=1:q #chodzimy po wszystkich blokach
		for k=(p-1)*l+1:p*l-1 #k to zmienna wskazujaca na element pod ktorym bedziemy zerowac
			for i=k+1:p*l #zerujemy w dol az do konca bloku
				z = m[i, k]/m[k, k] #mnoznik w danym wierszu
				m[i, k] = 0.0 #zerujemy komorke
				for j=k+1:p*l #aktualizacja komorek w bloku
					m[i, j] -= z*m[k, j]
				end
				#jesli nie jestesmy w ostatnim bloku to aktualizujemy komorki wystepujace pomiedzy A a C
				if p!=q	
					for j=p*l+1:k+l
						m[i, j] -= z*m[k, j]
					end
				end
				b[i] -= z*b[k] #aktualizujemy jeszcze wektor B
			end
		end
		#czesc odpowiedzialna za zerowanie macierzy B, robimy to dla wszystkich blokow ale nie w ostatnim
		if p!=q
			k=p*l #w tej komorce wystepuje macierz B
			for i=k+1:k+l #zerujemy B do samego dolu nastepnego bloku
				z = m[i, k]/m[k, k] #mnoznik wiersza
				m[i, k] = 0.0 #zeruj b
				#aktualizacja wierszy w bloku nastepnym
				for j=k+1:k+l
					m[i, j] -= z*m[k, j]
				end
				#aktualizacja wektora prawych stron B
				b[i] -= z*b[k]
			end
		end
	end
end

#Funkcja modyfikujaca macierz oraz wektor B w ten sposob ze zeruje komorki
#z macierzy trojkatnej dolnej, oraz aktualizuje wartosci poszczegolnych komorek.
#Funkcja wykonuje to z czesciowym wyborem elementu glownego. Zwraca wektor
#permutacji p.
function modifiedGaussPartialPivoting(n::Int64, l::Int64, m, b)
	q = convert(Int64, n/l)
	p = Array{Int64}(n)
	for i=1:n
		p[i]=i
	end
	for r=1:q
		for k=(r-1)*l+1:r*l-1
			t = max_in_column(m, p, k, k, r*l)
			swap(p, k, t)
			for i=k+1:r*l
				z = m[p[i], k]/m[p[k],k]
				m[p[i],k]=0.0
				for j=k+1:r*l
					m[p[i], j] -= z*m[p[k],j]
				end
				if r!=q
					for j=(r-1)*l+1:k
						m[p[i],p[j]+l] -= z*m[p[k],p[j]+l]
					end
				end
				b[p[i]] -= z*b[p[k]] #aktualizujemy jeszcze wektor B
			end
		end
		#przed wyjsciem z bloku czyscimy B
		if r!=q
			k = r*l
			t = max_in_column(m, p, k, k, k+l)
			swap(p, k, t)
			for j=k+1:k+l
				z = m[p[j], k]/m[p[k], k]
				m[p[j], k] = 0.0
				for i=k+1:k+l
					m[p[j],i] -= z*m[p[k], i]
				end
				b[p[j]] -= z*b[p[k]] #aktualizujemy jeszcze wektor B
			end
		end
	end
	return p
end



###########################################################################
#### OBLICZANIE Ax=b GDY A JEST MACIERZA TROJKATNA GORNA ##################
###########################################################################

#Funkcja obliczajaca wartosc wektora X dla danej, zmodyfikowanej juz macierzy trojkatnej gornej
#m, oraz wektora prawych stron b
function countGauss(n::Int64, l::Int64, m, b)
	x = Array{Float64}(n) #wektor x w ktorym bedzie rozwiazanie
	q = convert(Int64, n/l) #ilosc blokow
	for pt=1:q #chodzimy po blokach
		p = q - pt + 1 #modyfikacja do downto
		for it=1:l #chodzimy po wierszach od dolu do gory
			i=p*l-it+1 #modyfikacja do downto
			s = 0.0 #suma wiersza
			e = n #dokad liczymy sume wiersza, jezeli jestesmy w ostatnim bloku to do p*l
			if p!=q #jezeli nie jestesmy w ostatnim bloku to do i+l (poniewaz jest macierz C)
				e = i+l
			end
			#licz sume calego wiersza poczawszy od prawego ode mnie
			for j=i+1:e
				s += m[i, j]*x[j]
			end
			x[i] = (b[i] - s)/m[i, i] #podstaw wartosc do x[i]
		end
	end
	return x
end

#Funkcja rozwiazujaca zmodyfikowane rownanie macierzowe, juz w postaci trojkatnej
#zwraca wektor X
function countGaussPartialPivoting(n::Int64, l::Int64, m, b, p)
	x = Array{Float64}(n) #tablica to przetrzymywania rozwiazania
	q = convert(Int64, n/l) #ilosc blokow
	for pt=1:q #chodzimy po blokach od dolu do gory
		r = q - pt + 1 #modyfikacja do downto
		for it=1:l #chodzimy po wierszach od dolu do gory
			i=r*l-it+1 #modyfikacja do downto
			s=0.0 #suma aktualnego wiersza
			#sumujemy od ostatniego elementu w wektorze permutacji
			#(ostatni element jest na samym dole
			for j=i+1:r*l #liczymy sume w trojkatnym bloku A
				s += m[p[i],j]*x[j] 
			end
			#jezeli nie jestesmy w ostatnim bloku to musimy wysumowac jeszcze
			#blok C
			if r!=q
				#lecimy od pierwszego wiersza z bloku i sumujemy te elementy
				#z bloku C
				for j=(r-1)*l+1:i #lecimy do aktualnie przetwarzanego wiersza (od poczatku bloku)
					s += m[p[i], p[j]+l]*x[p[j]+l] #sumujemy C
				end
			end
			x[i] = (b[p[i]]-s)/m[p[i],i]
		end
	end
	return x
end


###########################################################################
################## OBLICZANIE ROZKLADU LU #################################
###########################################################################

#Funkcja obliczajaca rozklad LU dla macierzy m, bez czesciowego wyboru elementu glownego. 
#Modyfikuje macierz wewnątrz funkcji.
function getLU(n::Int64, l::Int64, m)
        q = convert(Int64, n/l)
        for p=1:q #chodzimy po wszystkich blokach
                for k=(p-1)*l+1:p*l-1 #k to zmienna wskazujaca na element pod ktorym bedziemy zerowac
                        for i=k+1:p*l #zerujemy w dol az do konca bloku
                                z = m[i, k]/m[k, k] #mnoznik w danym wierszu
                                m[i, k] = z #wstawiamy element do macierzy L
                                for j=k+1:p*l #aktualizacja komorek w bloku
                                        m[i, j] -= z*m[k, j]
                                end
                                #jesli nie jestesmy w ostatnim bloku to aktualizujemy komorki wystepujace pomiedzy A a C
                                if p!=q
                                        for j=p*l+1:k+l
                                                m[i, j] -= z*m[k, j]
                                        end
                                end
                        end
                end
                #czesc odpowiedzialna za zerowanie macierzy B, robimy to dla wszystkich blokow ale nie w ostatnim
                if p!=q
                        k=p*l #w tej komorce wystepuje macierz B
                        for i=k+1:k+l #zerujemy B do samego dolu nastepnego bloku
                                z = m[i, k]/m[k, k] #mnoznik wiersza
                                m[i, k] = z #wstawiamy element do macierzy L
                                #aktualizacja wierszy w bloku nastepnym
                                for j=k+1:k+l
                                        m[i, j] -= z*m[k, j]
                                end
                        end
                end
        end
end

#Funkcja obliczajaca rozklad LU dla macierzy m, z czesciowym wyborem elementu glownego. 
#Modyfikuje macierz wewnątrz funkcji.
function getLU_PartialPivoting(n::Int64, l::Int64, m)
        q = convert(Int64, n/l)
        p = Array{Int64}(n)
        for i=1:n
                p[i]=i
        end
        for r=1:q
                for k=(r-1)*l+1:r*l-1
                        t = max_in_column(m, p, k, k, r*l)
                        swap(p, k, t)
                        for i=k+1:r*l
                                z = m[p[i], k]/m[p[k],k]
                                m[p[i],k]=z
                                for j=k+1:r*l
                                        m[p[i], j] -= z*m[p[k],j]
                                end
                                if r!=q
                                        for j=(r-1)*l+1:k
                                                m[p[i],p[j]+l] -= z*m[p[k],p[j]+l]
                                        end
                                end
                        end
                end
                #przed wyjsciem z bloku czyscimy B
                if r!=q
                        k = r*l
                        t = max_in_column(m, p, k, k, k+l)
                        swap(p, k, t)
                        for j=k+1:k+l
                                z = m[p[j], k]/m[p[k], k]
                                m[p[j], k] = z
                                for i=k+1:k+l
                                        m[p[j],i] -= z*m[p[k], i]
                                end
                        end
                end
        end
        return p

end


###########################################################################
########### OBLICZANIE Ax=b GDZIE A JEST W POSTACI LU #####################
###########################################################################

#Funkcja obliczajaca rownanie Ax=b gdzie A jest macierzą postaci LU bez czesciowego wyboru
#elementu glownego , oraz b jest wektorem prawych stron.
#Funkcja zwraca rozwiazanie, czyli wektor x.
function countGauss_withLU(n::Int64, l::Int64, m, b)
        q = convert(Int64, n/l)
        x = Array{Float64}(n)
        #obliczanie Lz=b
        for r=1:q
                for k=(r-1)*l+1:r*l-1 #ustawiam ktora kolumne bede odejmowal od prawych stron
                        for i=k+1:r*l
                                b[i]-=m[i,k]*b[k]
                        end
                end
                if r!=q
                        for i=r*l+1:r*l+l
                                b[i]-=m[i,r*l]*b[r*l]
                        end
                end
        end
        #obliczanie Ux=z
        x = countGauss(n,l,m,b)
        return x
end

#Funkcja obliczajaca rownanie Ax=b gdzie A jest macierzą postaci LU z czesciowym wyborem
#elementu glownego , oraz b jest wektorem prawych stron. Nalezy podac rownierz wektor
#permutacji p.
#Funkcja zwraca rozwiazanie, czyli wektor x.
function countGaussPartialPivoting_withLU(n::Int64, l::Int64, m, b, p)
	q = convert(Int64, n/l)
	#obliczanie Lz=b
	for r=1:q
		for k=(r-1)*l+1:r*l-1
			for i=k+1:r*l
				b[p[i]]-=m[p[i],k]*b[p[k]]
			end
		end
		if r!=q
			for i=r*l+1:r*l+l
				b[p[i]]-=m[p[i],r*l]*b[p[r*l]]
			end
		end
	end
	#obliczanie Ux=z
	x = countGaussPartialPivoting(n,l,m,b,p)
	return x
end

###########################################################################
########### FUNKCJE POMOCNICZE DO PARTIAL PIVOTING'U ######################
###########################################################################

#Funkcja szukajaca maksymalnej wartosci w kolumnie o indeksie kol, zaczynajac
#od wiersza w_od, konczac na wierszu w_do. Nalezy podac przegladana macierz,
#oraz wektor permutacji
function max_in_column(m, p, kol::Int64, w_od::Int64, w_do::Int64)
	max = abs(m[p[w_od], kol])
	t = w_od
	for j=w_od+1:w_do
		if abs(m[p[j], kol]) > max
			max = abs(m[p[j],kol])
			t = j
		end
	end
	return t
end

#Funkcja zamieniajaca miejscami indeksy i oraz j w wektorze permutacji p.
function swap(p, i::Int64, j::Int64)
	temp = p[i]
	p[i] = p[j]
	p[j] = temp
end


###########################################################################
########## MNOZENIE NASZEJ MACIERZY RAZY WEKTOR ###########################
###########################################################################

#Funkcja mnozaca nasza macierz rzadka m razy wektor x
#zwraca wyliczony wektor B
function multMatrixAndVector(n::Int64, l::Int64, m, x)
	q = convert(Int64, n/l) #ilosc blokow
	b = Array{Float64}(n) #tu bedzie wynik
	for p=1:q #chodzimy po blokach
		for i=(p-1)*l+1:p*l
			b[i] = 0.0
			#liczymy wartosci na macierzy A
			for j=(p-1)*l+1:p*l
				b[i] += m[i, j]*x[j]
			end
			#liczymy wartosci na macierzy B
			if p!=1
				b[i] += m[i, (p-1)*l]*x[(p-1)*l]
			end
			#liczymy wartosci na macierzy C
			if p!=q
				b[i] += m[i, i+l]*x[i+l]
			end
		end
	end
	return b
end

#Funkcja obliczajaca iloczyn macierzy znajdujacej sie w pliku m_file oraz x.
#Funkcja nastepnie oblicza na podstawie otrzymanego wektora b eliminacje gaussa
#wraz z macierza z pliku m_file otrzymujac x2. Nastepnie wpisuje do pliku o nazwie
#outputFile caly wektor x2, oraz blad wzgledny pomiedzy x2 a x.
function multMatrixAndVector_writeToFile(m_file::AbstractString, x, outputFile::AbstractString)
	n, l, m = readMatrixFromFile(m_file)
	b = multMatrixAndVector(n, l, m, x)
	modifiedGauss(n, l, m, b)
	x2 = countGauss(n, l, m, b)
	writeXToFileWithRelativeError(x2, x, outputFile)
end

#Funkcja obliczajaca iloczyn macierzy znajdujacej sie w pliku m_file oraz macierzy ones(n).
#Funkcja nastepnie oblicza na podstawie otrzymanego wektora b eliminacje gaussa
#wraz z macierza z pliku m_file otrzymujac x2. Nastepnie wpisuje do pliku o nazwie
#outputFile caly wektor x2, oraz blad wzgledny pomiedzy x2 a ones(n).
#zmienne isPartialPivoting oraz isLU mowia jaka funkcja ma wykonywac mnozenie.
function multMatrixAndOnes_writeToFile(m_file::AbstractString, isPartialPivoting::Int, isLU::Int, outputFile::AbstractString)
	n, l, m = readMatrixFromFile(m_file)
	b = multMatrixAndVector(n, l, m, ones(n))
	x2 = Array{Float64,1}
	if isPartialPivoting == 1
		if isLU == 1
			n,l, m, p = getLUPartialPivoting_fromFile(m_file)
			x2 = countGaussPartialPivoting_withLU(n, l, m, b, p)
		else
			p = modifiedGaussPartialPivoting(n, l, m, b)
        		x2 = countGaussPartialPivoting(n, l, m, b, p)
		end
	else
		if isLU == 1
			n,l, m = getLU_fromFile(m_file)
			x2 = countGauss_withLU(n, l, m, b)
		else
			modifiedGauss(n, l, m, b)
			x2 = countGauss(n, l, m, b)
		end
	end
	writeXToFileWithRelativeError(x2, ones(n), outputFile)
end

###########################################################################
######## PODSTAWOWE FUNKCJE ###############################################
###########################################################################

#funkcja zwracajaca macierz w postaci LU (bez wyboru elementu glownego) 
#z pliku o nazwie m_file
function getLU_fromFile(m_file::AbstractString)
	n, l, m = readMatrixFromFile(m_file)
	getLU(n, l, m)
	return n, l, m
end

#funkcja zwracajaca macierz w postaci LU (z wyborem elementu glownego)
#z pliku o nazwie m_file
function getLUPartialPivoting_fromFile(m_file::AbstractString)
	n, l, m = readMatrixFromFile(m_file)
	p = getLU_PartialPivoting(n, l, m)
	return n, l, m, p
end

#Funkcja obliczajaca rownanie Ax=b (bez czesciowego wyboru elementu glownego) gdzie 
#A jest czytane z pliku o nazwie m_file, a b jest czytane z pliku o nazwie b_file.
#Funkcja zwraca rozwiazanie ukladu, czyli wektor x.
function my_GaussianElimination(m_file::AbstractString, b_file::AbstractString, output_file::AbstractString)
	n, l, m = readMatrixFromFile(m_file)
	z, b = readBvectorFromFile(b_file)
	modifiedGauss(n, l, m, b)
	x = countGauss(n, l, m, b)
	writeXToFile(x, output_file)
	return x
end

#Funkcja obliczajaca rownanie Ax=b (z czesciowym wyborem elementu glownego) gdzie 
#A jest czytane z pliku o nazwie m_file, a b jest czytane z pliku o nazwie b_file.
#Funkcja zwraca rozwiazanie ukladu, czyli wektor x.
function my_GaussianEliminationPartialPivoting(m_file::AbstractString, b_file::AbstractString, output_file::AbstractString)
        n, l, m = readMatrixFromFile(m_file)
        z, b = readBvectorFromFile(b_file)
        p = modifiedGaussPartialPivoting(n, l, m, b)
        x = countGaussPartialPivoting(n, l, m, b, p)
	writeXToFile(x, output_file)
	return x
end

#Funkcja obliczajaca rownanie Ax=B (bez czesciowego wyboru elementu glownego) gdzie
#A jest podane w postaci LU bez czesciowego wyboru elementu glownego, oraz b jest
#czytane z pliku o nazwie b_file.
#Funkcja zwraca rozwiazanie ukladu, czyli wektor x.
function my_GaussianElimination_withLU(m, n, l, b_file::AbstractString, output_file::AbstractString)
        z, b = readBvectorFromFile(b_file)
        x = countGauss_withLU(n, l, m, b)
	writeXToFile(x, output_file)
	return x
end

#Funkcja obliczajaca rownanie Ax=B (z czesciowym wyborem elemetu glownego) gdzie
#A jest podane w postaci LU z czesciowym wyborem elementu glownego, oraz b jest
#czytane z pliku o nazwie b_file. Nalezy podac rowniez wektor permutacji p.
#Funkcja zwraca rozwiazanie ukladu, czyli wektor x.
function my_GaussianEliminationPartialPivoting_withLU(m, n, l, p, b_file::AbstractString, output_file::AbstractString)
        z, b = readBvectorFromFile(b_file)
        x = countGaussPartialPivoting_withLU(n, l, m, b, p)
	writeXToFile(x, output_file)
	return x
end

end
