function deleteNodes(ids){
	parent.subLeftFrame.deleteNodes(ids);
}

function addNode(id, code, name, mark){
	parent.subLeftFrame.addNode(id, code, name, mark);
}

function reloadTree(){
	parent.subLeftFrame.reloadTree();
}

function changeParent(ids, targetID){
	parent.subLeftFrame.changeParent(ids, targetID);
}
