<?php 
require_once("ModelPHP.php"); 
$P = new Page(1); 
$P->addCSS("problem_wiez_z_hanoi.css");
$P->addJS("problem_wiez_z_hanoi.js");
echo $P->Begin(); 
?> 

					<h2 id="hanoi" class="major">Tower of Hanoi</h2>
					<p cite="https://en.wikipedia.org/wiki/Tower_of_Hanoi">The Tower of Hanoi (also called the Tower of Brahma or Lucas' Tower, and sometimes pluralized) is a mathematical game or puzzle. It consists of three rods, and a number of disks of different sizes which can slide onto any rod. The puzzle starts with the disks in a neat stack in ascending order of size on one rod, the smallest at the top, thus making a conical shape.<br>
					The objective of the puzzle is to move the entire stack to another rod, obeying the following simple rules:</p>
					<ol>
						<li>Only one disk can be moved at a time.</li>
						<li>Each move consists of taking the upper disk from one of the stacks and placing it on top of another stack i.e. a disk can only be moved if it is the uppermost disk on a stack.</li>
						<li>No disk may be placed on top of a smaller disk.</li>
					</ol>
					<p>With three disks, the puzzle can be solved in seven moves. The minimum number of moves required to solve a Tower of Hanoi puzzle is 2n - 1, where n is the number of disks.
					</p>
					<h3 class="minor">Algorithm:</h3>
					<div class="row">
						<div class="col-6-6">
							<h4 class="minmin">Pseudocode:</h4>
							<code id="PSEUDOKOD_PANEL">Function Tower_Of_Hanoi(A, B, C):<br>
							If N==1 : Move disk from A to B<br>
							else<br>
							Tower_Of_Hanoi (N-1, A, C, B)<br>
							Move disk from A to B<br>
							Tower_Of_Hanoi (N-1, C, B, A)</code>
						</div>
						<div class="col-6-6">
							<h4 class="minmin">Javascript:</h4>
							<code id="JAVASCRIPT_PANEL">function hanoi(n, A, B, C){<br>
								&emsp;if(n==1) {S = S + A + " --> " + B +"\n";}<br>
								&emsp;else {<br>
									&emsp;&emsp;hanoi(n-1, A, C, B);<br>
									&emsp;&emsp;S = S + A + " --> " + B +"\n";<br>
									&emsp;&emsp;hanoi(n-1, C, B, A);<br>
								&emsp;}<br>
							}</code>
						</div>
					</div>
					
					<h3 class="minor">Example:</h3>
					<p>Set the number of disks: <input id="input" type="number" min="1" max="8"> <button id="ok">OK</button>  <span id="okInfo"></span></p>
					<!--div id="outcome"></div-->
					<div id="canvascont">
						<canvas id="hanoicanvas" width="700" height="450">Your browser does not support canvas.</canvas>
						<div id="controls">
							<div class="col-2-6">
								<button id="prev">Prev</button>
							</div>
							<div class="col-2-6">
								<button id="auto">Auto</button>
								<input id="speed" type="number" min="1" max="10">
							</div>
							<div class="col-2-6">
								<button id="next">Next</button>
							</div>
						</div>
					</div>
<?php echo $P->End(); ?>